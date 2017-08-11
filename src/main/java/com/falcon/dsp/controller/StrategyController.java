package com.falcon.dsp.controller;

import com.falcon.dsp.annotation.UserAttribute;
import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.common.MathUtil;
import com.falcon.dsp.enumration.FalStrategyStatus;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.exception.FalDspParamException;
import com.falcon.dsp.exception.FalconDspServerException;
import com.falcon.dsp.handler.RoleAndView;
import com.falcon.dsp.jdbc.entity.Campaign;
import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.jdbc.entity.Strategy;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.jdbc.model.TableParam;
import com.falcon.dsp.jdbc.model.Tree;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.param.StrategyParam;
import com.falcon.dsp.rest.campaign.params.CampaignParam;
import com.falcon.dsp.service.CampaignService;
import com.falcon.dsp.service.StragegyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 策略
 * @author dongbin.yu
 * @from 2016-04-01
 * @since V1.0
 */
@Controller
@RequestMapping("/strategy")
public class StrategyController extends BaseController {

    @Autowired
    private StragegyService stragegyService;

    @Autowired
    private CampaignService campaignService;

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public RoleAndView view(Model model, @RequestParam(value = "tabName", defaultValue = "strategy") String tabName, Integer cid, @UserAttribute UserModel user) {
        model.addAttribute("cid", cid);
        try {
            CampaignParam campaignParam = new CampaignParam();
            campaignParam.setCid(cid);
            campaignParam.setUid(user.getUid());
            Campaign campaign = campaignService.read(campaignParam,user.getRate());
            model.addAttribute("campaignName", campaign.getCname());
        } catch (Exception ex) {
            throw new FalconDspServerException(ex.getMessage(), ex);
        }

        if ("strategy".equals(tabName)) {

            model.addAttribute("tabName", "strategy");
            model.addAttribute("title", "策略管理");
        } else {

            model.addAttribute("tabName", "creative");
            model.addAttribute("title", "创意管理");
        }
        return new RoleAndView("CampaignInfo", user);
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public RoleAndView detail(Model model,
                              @RequestParam(value = "cid", defaultValue = "") String cid,
                              @RequestParam(value = "strategyid", defaultValue = "") String strategyid,
                              @UserAttribute UserModel user) {

        if (!strategyid.isEmpty()) {
            try {
                StrategyParam strategyParam = new StrategyParam();
                strategyParam.setId(Integer.parseInt(strategyid));
                Strategy strategy = stragegyService.read(strategyParam);
                StrategyParam mode = new StrategyParam();
                mode.putStrategyDBModel(strategy);
                model.addAttribute("strategy", JsonUtil.toJson(mode));
                model.addAttribute("strategyId", strategyid);
                CampaignParam campaignParam = new CampaignParam();
                campaignParam.setCid(strategy.getCampaignId());
                model.addAttribute("cid", strategy.getCampaignId());
                Campaign campaign = campaignService.read(campaignParam,user.getRate());
                model.addAttribute("campaignName", campaign.getCname());
                model.addAttribute("strategy_name", strategy.getSname());
                model.addAttribute("start", DateUtils.dateToString(strategy.getStart(), "yyyy-MM-dd"));
                model.addAttribute("end", DateUtils.dateToString(strategy.getEnd(), "yyyy-MM-dd"));
                model.addAttribute("description", strategy.getDescription());
                model.addAttribute("bid", MathUtil.doubleFormat(strategy.getBid()/user.getRate()));
            } catch (Exception ex) {
                throw new FalconDspServerException(ex.getMessage(), ex);
            }
        }
        if (!cid.isEmpty()) {
            try {
                CampaignParam campaignParam = new CampaignParam();
                campaignParam.setCid(Integer.parseInt(cid));
                Campaign campaign = campaignService.read(campaignParam,user.getRate());
                model.addAttribute("campaignName", campaign.getCname());
                model.addAttribute("cid", cid);
            } catch (Exception ex) {
                throw new FalconDspServerException(ex.getMessage(), ex);
            }
        }

        return new RoleAndView("StrategyDetail", user);
    }

    /**
     * 策略列表
     *
     * @param tableParam
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public TableData<Strategy> list(@RequestBody TableParam tableParam,@UserAttribute UserModel user) {

        if (tableParam.getCampaignId() == null) {
            throw new FalDspParamException("缺少订单编号");
        }
        return stragegyService.list(tableParam,user);
    }

    @RequestMapping(value = "status", method = RequestMethod.GET)
    @ResponseBody
    public List<Tree> status() {
        List<Tree> result = new ArrayList<>();
        for (FalStrategyStatus item : FalStrategyStatus.values()) {
            if (item.getValue() == -1) {
                continue;
            }
            result.add(new Tree(item.getValue(), item.getDescription()));
        }
        return result;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody StrategyParam strategyParam, @UserAttribute UserModel user) {
        Integer uid;

        if (strategyParam.getCreativeId() == null) {
            return new Response().failure("请选择创意");
        }

        if (user.getUserRole() == UserRoleType.ADVERTISER) {
            uid = user.getUid();
        } else {
            int campaignId = strategyParam.getCid();
            CampaignParam campaignParam = new CampaignParam();
            campaignParam.setCid(campaignId);
            Campaign campaign = campaignService.read(campaignParam,user.getRate());
            uid = campaign.getUid();
        }

        if (uid == null) {
            return new Response().failure("缺少广告主编号");
        }

        strategyParam.setUid(uid);
        // 从前端拿到的策略出价数据单位为元，转化为分并markup
        strategyParam.setBid(strategyParam.getBid() * 100 * user.getRate());
        Strategy strategy = stragegyService.create(strategyParam,user.getRate());
        return new Response().success(strategy);
    }

    @RequestMapping(value = "read", method = RequestMethod.GET)
    @ResponseBody
    public Response read(StrategyParam strategyParam, @UserAttribute UserModel user) {
        Strategy strategy = stragegyService.read(strategyParam);
        return new Response().success(strategy);
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody StrategyParam strategyParam, @UserAttribute UserModel user) {
        Integer uid;

        if (user.getUserRole() == UserRoleType.ADVERTISER) {
            uid = user.getUid();
        } else if (strategyParam.getCid() != null) {
            int campaignId = strategyParam.getCid();
            CampaignParam campaignParam = new CampaignParam();
            campaignParam.setCid(campaignId);
            Campaign campaign = campaignService.read(campaignParam,user.getRate());
            uid = campaign.getUid();
        } else {
            if (strategyParam.getId() == null) {
                return new Response().failure("参数错误");
            }
            Strategy strategy = stragegyService.read(strategyParam);
            uid = strategy.getUid();
        }

        if (uid == null) {
            return new Response().failure("缺少广告主编号");
        }
        strategyParam.setUid(uid);
        // 从前端拿到的策略出价数据单位为元，转化为分并markup
        if(strategyParam.getBid()!=null){
            strategyParam.setBid(strategyParam.getBid() * 100 * user.getRate());
        }
        if (strategyParam.getId() == null) {
            return new Response().success(stragegyService.create(strategyParam,user.getRate()));
        }
        return new Response().success(stragegyService.edit(strategyParam,user.getRate()));
    }

    @RequestMapping(value = "utility", method = RequestMethod.POST)
    @ResponseBody
    public Response utility(@RequestBody StrategyParam strategyParam, @UserAttribute UserModel user) {
        Integer uid;

        if (user.getUserRole() == UserRoleType.ADVERTISER) {
            uid = user.getUid();
        } else {
            int campaignId = strategyParam.getCid();
            CampaignParam campaignParam = new CampaignParam();
            campaignParam.setCid(campaignId);
            Campaign campaign = campaignService.read(campaignParam,user.getRate());
            uid = campaign.getUid();
        }

        if (uid == null) {
            return new Response().failure("缺少广告主编号");
        }
        strategyParam.setUid(uid);
        // 从前端拿到的策略出价数据单位为元，转化为分并markup
        if(strategyParam.getBid()!=null){
            strategyParam.setBid(strategyParam.getBid() * 100 * user.getRate());
        }
        return new Response().success(stragegyService.getUtility(strategyParam,user));
    }
}
