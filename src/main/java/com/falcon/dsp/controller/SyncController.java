package com.falcon.dsp.controller;

import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.rest.advertiser.response.GdtAdvertiser;
import com.falcon.dsp.rest.advertiser.response.GdtAdvertiserResult;
import com.falcon.dsp.service.AdvertiserService;
import com.falcon.dsp.service.StragegyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Zhouchunhui
 * @from 2016-05-06
 * @since V1.0
 */
@Controller
@RequestMapping("/sync")
public class SyncController extends BaseController {

    @Autowired
    AdvertiserService advertiserService;

    @Autowired
    StragegyService stragegyService;


    /**
     * 从广点通 同步广告主基本 信息 写入advertiser
     *
     * @return
     */
    @RequestMapping(value = "advertiser", method = RequestMethod.GET)
    @ResponseBody
    public Response syncAdvertiser(@RequestParam(value = "uid", required = true) Integer uid, @RequestParam(value = "agency_id", required = true)
    Integer agencyId) {
        GdtAdvertiserResult result = advertiserService.getGdtAdvertiserByUid(uid, null);
        GdtAdvertiser ga = result.getData();
        ga.setAgencyId(agencyId);
        ga.setUid(uid);
        advertiserService.insertAdvertiser(ga);
        return new Response().success("ok!");
    }

    /**
     * 同步订单信息，包含 订单、策略、素材、创意
     *
     * @param uid  广告主id
     * @param cids 订单号，多个以逗号隔开，为空时查询广告主下所有 有效订单。
     * @return 执行结果成功或者失败
     */
    @RequestMapping(value = "campaign", method = RequestMethod.GET)
    @ResponseBody
    public Response syncCampaign(@RequestParam(value = "uid", required = true) Integer uid, @RequestParam(value = "cids", required = false) String cids) {

        stragegyService.syncCampaign(uid, cids);

        return new Response().success("ok!");
    }

    /**
     * 同步广告主信息及广告主下的订单信息
     *
     * @param uid      广告主id
     * @param agencyId 代理商ID
     * @return 执行结果成功或者失败
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    @ResponseBody
    public Response init(@RequestParam(value = "uid", required = true) Integer uid,
                         @RequestParam(value = "agency_id", required = true) Integer agencyId) {
        GdtAdvertiserResult result = advertiserService.getGdtAdvertiserByUid(uid, null);
        GdtAdvertiser ga = result.getData();
        ga.setAgencyId(agencyId);
        ga.setUid(uid);
        advertiserService.insertAdvertiser(ga);
        stragegyService.syncCampaignByUid(uid);
        return new Response().success("ok!");


    }
}
