package com.falcon.dsp.controller;

import com.falcon.dsp.annotation.UserAttribute;
import com.falcon.dsp.common.Constant;
import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.enumration.CustomerStatus;
import com.falcon.dsp.enumration.FinanceStatus;
import com.falcon.dsp.handler.RoleAndView;
import com.falcon.dsp.jdbc.entity.Advertiser;
import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.jdbc.model.Tree;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.param.AdvertiserParam;
import com.falcon.dsp.service.AdvertiserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 广告主
 * @author dongbin.yu
 * @from 2016-04-01
 * @since V1.0
 */
@Controller
@RequestMapping("/advertiser")
public class AdvertiserController {
	
	private static Pattern pattern = Pattern.compile("^(data:.*;base64,)");

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private AdvertiserService service;

    @Autowired
    private String resourceAddress;

    @Autowired
    private String serverAddress;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public RoleAndView list(Model model, @UserAttribute UserModel user) {
        return new RoleAndView("AdvertiserList", user);
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @ResponseBody
    public List<Tree> list(String searchKeyword, @UserAttribute UserModel user) {

        return service.tree(user.getUid(), searchKeyword);
    }

    /**
     * 广告主显示详细信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public RoleAndView Detail(Model model, AdvertiserParam param, @UserAttribute UserModel user) {

        switch (user.getUserRole()) {
            case AGENCY:
                if (param.getUid() != null) {
                    Advertiser advertiser = service.selectOne(param.getUid());

                    if (advertiser != null) {
                        advertiser.qualificationStr2BeanJson();
                    }

                    model.addAttribute("advertiser", advertiser);
                    model.addAttribute("cs_status", advertiser.getUserStatusToInt());
                    model.addAttribute("advertiserData", JsonUtil.toJson(true));
                }
                return new RoleAndView("AdvertiserDetail", user);
            case ADVERTISER:

                param.setUid(user.getUid());
                Advertiser advertiser = service.selectOne(param.getUid());

                if (advertiser != null) {
                    advertiser.qualificationStr2BeanJson();
                }

                model.addAttribute("advertiser", advertiser);
                model.addAttribute("advertiserData", JsonUtil.toJson(true));
                model.addAttribute("cs_status", advertiser.getUserStatusToInt());
                return new RoleAndView("Advertiser", user);
            default:
                return new RoleAndView("Advertiser", user);
        }

    }
    
    /**
     * 广告主创建（代理商使用）
     * @param advertiserParam
     * @param session
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody AdvertiserParam advertiserParam,HttpSession session, @UserAttribute UserModel user){

    	advertiserParam.setAgencyId(user.getUid()); 
    	//添加默认
    	addDefaultVal(advertiserParam);

        Advertiser advertiser = service.addAdvertiser(advertiserParam);
        user.getAccessParam().addValue(Constant.PERMISSION_PARAM_UID,advertiser.getUid());
        session.setAttribute(Constant.USER_INFO_SESSION, user);
        return new Response().success("广告主创建成功");

    }
    
    
    /**
     * 新建时默认值。
     * @param advertiserParam
     */
    private void addDefaultVal(AdvertiserParam advertiserParam ) {

    	if(StringUtils.isEmpty(advertiserParam.getUname())){
            advertiserParam.setUname(advertiserParam.getCorporation());
        }
    	if(advertiserParam.getFundStatus()==null){
    		advertiserParam.setFundStatus(FinanceStatus.FUNDSTATUS_NOT_ENOUGH);
    	}
    	if(advertiserParam.getVirfundStatus()==null){    		
    		advertiserParam.setVirfundStatus(FinanceStatus.FUNDSTATUS_NOT_ACTIVED);
    	}
    	advertiserParam.setCustomerStatus(CustomerStatus.CUSTOMERSTATUS_PENDING);
	}

    /**
     * 广告主更新（代理商和广告主使用）
     *
     * @param advertiserParam
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody AdvertiserParam advertiserParam, @UserAttribute UserModel user) {

        service.updateAdvertiser(advertiserParam, user);
        return new Response().success("广告主更新成功");

    }

    /**
     * 解析图片（参考creative做法）
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public Response parseImage(String imageStr, HttpServletRequest request) throws IOException {

        Matcher matcher = pattern.matcher(imageStr);
        while (matcher.find()) {
            //处理字符开头的64位
            imageStr = imageStr.replace(matcher.group(1), "");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        String currentDate = DATE_FORMAT.format(new Date());
        String path = resourceAddress + "/upload/" + currentDate;

        /*String path = request.getSession().getServletContext().getRealPath("/upload");
        String currentDate = DATE_FORMAT.format(new Date());
        path += File.separator + currentDate;*/
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = UUID.randomUUID() + ".png";
        File file = new File(path, fileName);

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            byte[] bytes = decoder.decodeBuffer(imageStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }

            outputStream.write(bytes);
            outputStream.flush();
        }

        String relativePath = serverAddress + "/upload/" + currentDate + "/" + fileName;

        return new Response().success(relativePath);

    }

}
