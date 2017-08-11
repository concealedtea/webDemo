package com.falcon.dsp.controller;

import com.falcon.dsp.annotation.UserAttribute;
import com.falcon.dsp.common.Constant;
import com.falcon.dsp.common.MathUtil;
import com.falcon.dsp.common.MessageDigest;
import com.falcon.dsp.common.StringUtil;
import com.falcon.dsp.enumration.ErrorType;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.enumration.UserStatus;
import com.falcon.dsp.handler.RoleAndView;
import com.falcon.dsp.jdbc.entity.*;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.jdbc.model.TableParam;
import com.falcon.dsp.jdbc.model.UserListModel;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.param.UserInfoParam;
import com.falcon.dsp.param.UserParam;
import com.falcon.dsp.service.AdvertiserService;
import com.falcon.dsp.service.AgencyService;
import com.falcon.dsp.service.CampaignService;
import com.falcon.dsp.service.UserInfoService;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 * @author wei.wang
 * @from 2016-04-15
 * @since V1.0
 */
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    AdvertiserService advertiserService;

    @Autowired
    private AgencyService agencyService;


    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MessageDigest messageDigest;

    private final String USER_INFO_SESSION = Constant.USER_INFO_SESSION;

    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    /**
     * 首页
     * @param user
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public RoleAndView index(@UserAttribute UserModel user) {
        return new RoleAndView("index", user);
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 登录
     *
     * @param param
     * @param model
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/entry", method = RequestMethod.POST)
    @ResponseBody
    public Response entry(UserInfoParam param, Model model, HttpSession httpSession) throws Exception {
        boolean flg = true;
        UserInfo userInfo = null;
        if (StringUtil.hasTextWithoutWhiteSpace(param.getPassword())
                && StringUtil.hasTextWithoutWhiteSpace(param.getUserName())) {

            userInfo = new UserInfo();
            userInfo.setUserName(param.getUserName());
            userInfo = userInfoService.getUserInfoByUserName(userInfo);

            String passwordSalt = encrypt(param.getPassword());
            if (userInfo != null && passwordSalt.equals(userInfo.getPassword())) {
                flg = true;
            } else {
                flg = false;
            }
        } else {
            flg = false;
        }
        if (flg) {
            int status = userInfo.getStatus();
            if(status == UserStatus.NORMAL_STATUS.getStatus()){
                // 去掉密码
                userInfo.setPassword(null);
                //只有登录的用户打log
                logger.info("用户登录：" + userInfo.toString());
                UserModel userModel = new UserModel();
                userModel.settingValus(userInfo);
                String returnValue = "";
                switch (userModel.getUserRole()) {
                    case ADMIN:
                        AccessParam accessParam = new AccessParam();
                        userModel.setAccessParam(accessParam);
                        returnValue = "/agency/list.shtml";
                        break;
                    case AGENCY:
                        setAgencyResource(userModel);
                        returnValue = "/report/view.shtml";
                        break;
                    case ADVERTISER:
                        Advertiser advertiser = advertiserService.selectOne(userModel.getUid());
                        if(advertiser.getAgencyId()==Constant.LIEING_AGENCY_UID){
                            userModel.setInLieyingAccount(true);
                        }else{
                            userModel.setInLieyingAccount(false);
                        }
                        setAdvertiserResource(userModel);
                        returnValue = "/index.shtml";
                        break;
                }
                userModel.setRate(getUserCommission(userModel));
                setAttribute(httpSession, userModel, USER_INFO_SESSION);
                return new Response().success(returnValue);

            }else{
                setAttribute(httpSession, null, USER_INFO_SESSION);
                return new Response().failure(ErrorType.GDT_ERROR_9003.getDescription());
            }
        } else {
            setAttribute(httpSession, null, USER_INFO_SESSION);
            return new Response().failure(ErrorType.GDT_ERROR_9001.getDescription());
        }
    }

    /**
     * 广告主可以访问的资源。包括广告主和订单
     * @param userModel
     */
    private void setAdvertiserResource(UserModel userModel){
        AccessParam accessParam = new AccessParam();
        userModel.setAccessParam(accessParam);
        Integer uid = userModel.getUid();
        List<Integer> list = new ArrayList<>(1);
        list.add(uid);
        accessParam.put(Constant.PERMISSION_PARAM_UID,list);
        List<Integer> cidList =  campaignService.getCampaignTree(uid,null);
        accessParamPutValue(accessParam,Constant.PERMISSION_PARAM_CID,cidList);
        accessParamPutValue(accessParam,Constant.PERMISSION_PARAM_CAMPAIGN_ID,cidList);
    }

    private void accessParamPutValue(AccessParam accessParam ,String key,List list){
        if(list!=null&&list.size()>0){
            accessParam.put(key,list);
        }else{
            accessParam.put(key,new ArrayList());
        }
    }

    /**
     * 代理商可以访问的资源
     * @param userModel
     */
    private void setAgencyResource(UserModel userModel){
        AccessParam accessParam = new AccessParam();
        userModel.setAccessParam(accessParam);
        Integer uid = userModel.getUid();
        List<Integer> list =  advertiserService.uidList(uid);
        accessParamPutValue(accessParam,Constant.PERMISSION_PARAM_UID,list);
        List<Integer> cidList =  campaignService.getCampaignTree(null,list);
        accessParamPutValue(accessParam,Constant.PERMISSION_PARAM_CID,cidList);
        accessParamPutValue(accessParam,Constant.PERMISSION_PARAM_CAMPAIGN_ID,cidList);
    }


    /**
     * 获取到当前登录用户的服务费率
     * @param userInfo
     * @return
     */
    public double getUserCommission(UserInfo userInfo) {
        switch (userInfo.getUserRole()) {
            case AGENCY:
                Agency agency = agencyService.getAgencyInfo(userInfo.getUid());
                try {
                    return MathUtil.div(MathUtil.sub(100,agency.getCommission()),100);
                } catch (IllegalAccessException e) {
                   logger.error(e.getMessage());
                }
            case ADVERTISER:
                Advertiser advertiser = advertiserService.selectOne(userInfo.getUid());
                Agency agency1 = agencyService.getAgencyInfo(advertiser.getAgencyId());
                try {
                    return MathUtil.mul(MathUtil.div(MathUtil.sub(100,agency1.getCommission()),100),MathUtil.div(MathUtil.sub(100,advertiser.getCommission()),100));
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage());
                }
            default:
                return 1;
        }
    }

    /**
     * 登出
     *
     * @param param
     * @param model
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout")
    public String logout(UserInfoParam param, Model model, HttpSession httpSession) throws Exception {
        if (httpSession != null) {
            Object userInfo = getAttribute(httpSession, USER_INFO_SESSION);
            //只有登录的用户打log
            if (userInfo != null && userInfo instanceof UserInfo) {
                logger.info("用户登出：" + ((UserInfo) userInfo).toString());
            }
            setAttribute(httpSession, null, USER_INFO_SESSION);
        }
        return "login";
    }

    /**
     * 从session中获取数据
     *
     * @param httpSession
     * @param attrName
     * @throws Exception
     */
    private Object getAttribute(HttpSession httpSession, String attrName) throws Exception {
        synchronized (httpSession) {
            return httpSession.getAttribute(attrName);
        }
    }

    /**
     * 在session中存放数据
     *
     * @param httpSession
     * @param userInfo
     * @param attrName
     * @throws Exception
     */
    private void setAttribute(HttpSession httpSession, UserInfo userInfo, String attrName) throws Exception {
        synchronized (httpSession) {
            httpSession.setAttribute(attrName, userInfo);
        }
    }


    /**
     * 加密
     *
     * @param data
     * @return
     */
    private String encrypt(String data) {
        if (messageDigest == null) {
            return DigestUtils.sha1Hex(data);
        } else {
            return Hex.encodeHexString(messageDigest.encrypt(data));
        }
    }

    /**
     * 创建一个新的用户。
     *
     * @param param
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userInfo/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody UserInfoParam param, Model model) throws Exception {
        UserInfo userInfoForInsert = new UserInfo();
        userInfoForInsert.setPassword(encrypt(param.getPassword()));
        userInfoForInsert.setUid(param.getUid());
        userInfoForInsert.setUserName(param.getUserName());
        /**
         * check is exist
         */
        // 角色
        if ("agency".equals(param.getUserRole())) {
            userInfoForInsert.setUserRole(UserRoleType.AGENCY);
            //将绑定的对应的代理商id设置成账户表的uid
            userInfoForInsert.setUid(param.getUid());
        } else if ("admin".equals(param.getUserRole())) {
            userInfoForInsert.setUserRole(UserRoleType.ADMIN);
        } else {
            userInfoForInsert.setUserRole(UserRoleType.ADVERTISER);
            userInfoForInsert.setUid(param.getUid());
        }
        // 状态
        userInfoForInsert.setStatus(param.getStatus());
        userInfoService.insertOne(userInfoForInsert);
        logger.info("成功新建用户：" + userInfoForInsert.toString());
        return new Response().success(userInfoForInsert);
    }

    /**
     * 获取用户。
     *
     * @param param
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userInfo/toEdit")
    public RoleAndView edit(Model model, UserInfoParam param, @UserAttribute UserModel user) {

        UserInfo userInfoForSelect = new UserInfo();
        userInfoForSelect.setUserName(param.getUserName());

        userInfoForSelect = userInfoService.getUserInfoByUserName(userInfoForSelect);
        model.addAttribute("userInfo", userInfoForSelect);

        return new RoleAndView("UserView", user);

    }

    /**
     * 超级用户使用 创建用户页面
     *
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/newUserView", method = RequestMethod.GET)
    public RoleAndView newUserView(Model model, @UserAttribute UserModel user) {
        model.addAttribute("title", "首页");
        return new RoleAndView("UserView", user);
    }

    /**
     * 代理商用户管理
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/view", method = RequestMethod.GET)
    public RoleAndView userListView(Model model, @UserAttribute UserModel user) {
        model.addAttribute("title","用户管理");
        return new RoleAndView("UserList", user);
    }

    /**
     * 用于代理商 获取其下的登录账户列表
     *
     * @param param 查询参数列表
     * @param user 当前登录账户
     * @return
     */
    @RequestMapping(value = "/user/list", method = RequestMethod.POST)
    @ResponseBody
    public TableData<UserListModel> getUserList(@RequestBody TableParam param, @UserAttribute UserModel user) {
        return userInfoService.getUserList(param, user);
    }

    /**
     * 用于代理商账户的用户管理，创建一个新的用户。
     * @param param 用户信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    @ResponseBody
    public Response createUser(@RequestBody UserParam param, @UserAttribute UserModel user){
        UserInfo userInfoForInsert = getUserInfo(param, user);
        userInfoService.insertOne(userInfoForInsert);
        logger.info("成功新建用户：" + userInfoForInsert.toString());
        return new Response().success(userInfoForInsert);
    }

    /**
     * 根据id 查询登录账户信息
     * @param id 登录账户id
     * @return
     */
    @RequestMapping(value = "/user/read", method = RequestMethod.GET)
    @ResponseBody
    public UserListModel userRead(@RequestParam(value = "id") int id) {
        return userInfoService.read(id);
    }

    /**
     * 用于代理商账户的用户管理，更新用户信息。
     *
     * @param param 用户信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    @ResponseBody
    public UserListModel updateUser(@RequestBody UserParam param, @UserAttribute UserModel user){
        UserInfo userInfoForInsert = getUserInfo(param, user);
        userInfoForInsert.setId(param.getId());
        // 名称重复验证
        userInfoService.update(userInfoForInsert);
        logger.info("更新用户：" + userInfoForInsert.toString());
        return userInfoService.read(param.getId());
    }

    /**
     * 重组成写入的UserInfo
     * @param param
     * @param user
     * @return
     */
    private UserInfo getUserInfo(UserParam param,UserModel user) {
        UserInfo userInfoForInsert = new UserInfo();
        userInfoForInsert.setUserName(param.getUserName());
        if(StringUtils.isNotEmpty(param.getPassword())){
            userInfoForInsert.setPassword(encrypt(param.getPassword()));
        }
        // 角色
        if (UserRoleType.AGENCY == param.getUserRole()) {
            userInfoForInsert.setUserRole(UserRoleType.AGENCY);
            //将绑定的对应的代理商id设置成账户表的uid
            userInfoForInsert.setUid(user.getUid());
        } else if (UserRoleType.ADVERTISER == param.getUserRole()) {
            userInfoForInsert.setUserRole(UserRoleType.ADVERTISER);
            // 将绑定的对应的广告主id设置成账户表的uid
            userInfoForInsert.setUid(param.getUid());
        }

        // 状态
        userInfoForInsert.setStatus(param.getStatus());
        return userInfoForInsert;
    }


    /**
     * 更新用户状态
     *
     * @param userName
     * @param status
     * @return
     */
    // @ResponseBody
    // @RequestMapping(value = "/userInfo/updateStatus")
    // public Response updateUserStatus(@RequestParam(value = "user_name",
    // required = true) String userName,
    // @RequestParam(value = "status", required = true) Integer status
    // ) {
    // try {
    // userInfoService.updateUserStatus(userName, status);
    // } catch (Exception e) {
    // e.printStackTrace();
    // Map<String, String> map = new HashMap<String, String>();
    // map.put("更新用户状态错误：", e.getCause().getMessage());
    // return new Response().failure(JsonUtil.toJson(map));
    // }
    // return new Response().success("更新用户状态成功！");
    // }
}
