package com.falcon.dsp.controller;

import com.falcon.dsp.annotation.UserAttribute;
import com.falcon.dsp.common.Constant;
import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.enumration.ErrorType;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.handler.RoleAndView;
import com.falcon.dsp.jdbc.entity.Campaign;
import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.jdbc.model.TableParam;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.rest.campaign.params.CampaignParam;
import com.falcon.dsp.service.CampaignService;
import com.falcon.dsp.service.StragegyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 订单
 * @author dongbin.yu
 * @from 2016-03-29
 * @since V1.0
 */
@Controller
@RequestMapping("/campaign")
public class CampaignController extends BaseController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private StragegyService stragegyService;



    @RequestMapping(value = "view", method = RequestMethod.GET)
    public RoleAndView view(Model model, @UserAttribute UserModel user) {

        return new RoleAndView("CampaignList", user);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody Campaign campaign, @UserAttribute UserModel user, HttpSession session) {

        Integer uid;
        if (user.getUserRole() == UserRoleType.ADVERTISER) {
            uid = user.getUid();
        } else {
            uid = campaign.getUid();
        }
        if (uid == null) {
            return new Response().failure("缺少广告主编号");
        }
        campaign.setUid(uid);
        campaign.setCreateTime(new Date());
        campaign.setCtype(0);
        if (!campaignService.checkName(campaign)) {
            return new Response().failure(ErrorType.GDT_ERROR_7004.getDescription());
        }
        if (campaign.getCid() == null) {
            Campaign c = campaignService.create(campaign,user.getRate());
            user.getAccessParam().addValue(Constant.PERMISSION_PARAM_CID,c.getCid());
            user.getAccessParam().addValue(Constant.PERMISSION_PARAM_CAMPAIGN_ID,c.getCid());
            session.setAttribute(Constant.USER_INFO_SESSION, user);
            return new Response().success(c);
        } else {
            return new Response().success(campaignService.edit(campaign,user));
        }
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public TableData<Campaign> list(@RequestBody TableParam tableParam, @UserAttribute UserModel user) {
        int start = tableParam.getStart();
        int page = tableParam.getLength();
        CampaignParam campaignParam = new CampaignParam();
        campaignParam.setPage(start / page + 1);
        campaignParam.setPageSize(page);

        if (tableParam.getSearchAdvertiser() != null) {
            campaignParam.setUid(tableParam.getSearchAdvertiser());
        }

        if (user.getUserRole() == UserRoleType.AGENCY) {
            campaignParam.setAgencyId(user.getUid());
        } else {
            campaignParam.setUid(user.getUid());
        }

        if (tableParam.getOrderby() != null && tableParam.getOrdercolumns() != null) {
            campaignParam.setOrderby(tableParam.getOrderby());
            campaignParam.setOrderbyColumn(tableParam.getOrdercolumns());
        }

        if (tableParam.getSearchKeyword() != null) {
            Pattern pattern = Pattern.compile("[0-9,;]+");
            Matcher matcher = pattern.matcher(tableParam.getSearchKeyword());
            if (matcher.matches()) {
                String[] cids = tableParam.getSearchKeyword().replaceAll(";", ",").split(",");
                campaignParam.addWhereCondition("cid", Arrays.asList(cids));
            }
            campaignParam.addWhereCondition("cname", tableParam.getSearchKeyword());
        }
        if (tableParam.getSearchStatus() != null) {
            campaignParam.addWhereCondition("status", tableParam.getSearchStatus());
        }

        // 设置markup值
        campaignParam.addWhereCondition("markup", user.getRate());
        
        return campaignService.listTable(campaignParam);
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody Campaign campaign, @UserAttribute UserModel user) {

        Integer uid;
        if (user.getUserRole() == UserRoleType.ADVERTISER) {
            uid = user.getUid();
        } else if (campaign.getUid() != null) {
            uid = campaign.getUid();
        } else {
            if (campaign.getCid() == null) {
                return new Response().failure("参数错误");
            }
            CampaignParam campaignParam = new CampaignParam();
            campaignParam.setCid(campaign.getCid());
            Campaign data = campaignService.read(campaignParam,user.getRate());
            uid = data.getUid();
        }
        if (uid == null) {
            return new Response().failure("缺少广告主编号");
        }
        campaign.setUid(uid);

        Map<String, Date> timeRange = stragegyService.getTimaRange(campaign.getCid());
        if (timeRange != null && campaign.getStart() != null && campaign.getStart().getTime() > timeRange.get("start").getTime()) {
            return new Response().failure("订单开始时间必须小于策略开始时间");
//            return new Response().failure("订单开始时间必须小于策略开始时间，当前策略最小开始时间为" + DateUtils.dateToString(timeRange.get("start"), "yyyy-MM-dd"));
        }
        if (timeRange != null && campaign.getEnd() != null && DateUtils.endDay(campaign.getEnd()).getTime() < timeRange.get("end").getTime()) {
            return new Response().failure("订单结束时间必须大于策略结束时间");
//            return new Response().failure("订单结束时间必须大于策略结束时间,当前策略最大结束时间为" + DateUtils.dateToString(timeRange.get("end"), "yyyy-MM-dd"));
        }

        return new Response().success(campaignService.edit(campaign,user));
    }

    @RequestMapping(value = "read", method = RequestMethod.GET)
    @ResponseBody
    public Response read(@RequestParam Integer cid, @UserAttribute UserModel user) {
        CampaignParam campaignParam = new CampaignParam();
        campaignParam.setCid(cid);
        Campaign campaign = campaignService.read(campaignParam,user.getRate());
        return new Response().success(campaign);
    }

}

