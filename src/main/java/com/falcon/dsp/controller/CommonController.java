package com.falcon.dsp.controller;

import com.falcon.dsp.enumration.DateType;
import com.falcon.dsp.enumration.FalAdKpi;
import com.falcon.dsp.enumration.FalAdStatus;
import com.falcon.dsp.enumration.SpeedMode;
import com.falcon.dsp.enumration.target.*;
import com.falcon.dsp.jdbc.entity.AdSpacePosition;
import com.falcon.dsp.jdbc.model.AdSpacePositionModel;
import com.falcon.dsp.jdbc.model.Tree;
import com.falcon.dsp.service.AdSpacePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private AdSpacePositionService positionService;

    @RequestMapping(value = "status", method = RequestMethod.GET)
    @ResponseBody
    public List<Tree> status() {
        List<Tree> result = new ArrayList<>();
        for (FalAdStatus item : FalAdStatus.values()) {
            if (item.getValue() == -1) {
                continue;
            }
            result.add(new Tree(item.getValue(), item.getDescription()));
        }
        return result;
    }

    @RequestMapping(value = "kpis", method = RequestMethod.GET)
    @ResponseBody
    public List<Tree> kpis() {
        List<Tree> result = new ArrayList<>();
        for (FalAdKpi item : FalAdKpi.values()) {
            result.add(new Tree(item.getValue(), item.getDescription()));
        }
        return result;
    }

    @RequestMapping(value = "speedMode", method = RequestMethod.GET)
    @ResponseBody
    public List<Tree> speedMode() {
        List<Tree> result = new ArrayList<>();
        for (SpeedMode item : SpeedMode.values()) {
            result.add(new Tree(item.getValue(), item.getDescription()));
        }
        return result;
    }

    /**
     * 获取广告形式详细
     */
    @RequestMapping(value = "adSpacePosition", method = RequestMethod.GET)
    @ResponseBody
    public List<AdSpacePositionModel> adSpacePosition(@RequestParam(name = "platform_id", required = false) Integer platformId, @RequestParam(name = "ad_format_id", required = false) Integer adFormatId) {

        AdSpacePosition adSpacePosition = new AdSpacePosition();
        if (platformId != null) {
            adSpacePosition.setPlatformId(platformId);
        }

        if (adFormatId != null) {
            adSpacePosition.setAdFormatId(adFormatId);
        }

        return positionService.getAdSpacePositionList(adSpacePosition);
    }

    /**
     * 获取平台
     *
     * @return
     */
    @RequestMapping(value = "adPlatForm", method = RequestMethod.GET)
    @ResponseBody
    public List adFormat() {

        return positionService.getStrategyPlatformList();
    }

    @RequestMapping(value = "dateType", method = RequestMethod.GET)
    @ResponseBody
    public List<Tree> dateType() {
        List<Tree> result = new ArrayList<>();
        for (DateType item : DateType.values()) {
            result.add(new Tree(item.getValue(), item.getDescription()));
        }
        return result;
    }

    /**
     * 性别定向
     */
    @RequestMapping(value = "gender", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> gender() {

        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> map = null;
        for (Gender item : Gender.values()) {
            map = new HashMap<>();
            map.put("name", item.getName());
            map.put("value", item.getValue());
            result.add(map);
        }
        return result;
    }

    @RequestMapping(value = "connection", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> connection() {

        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> map = null;
        for (Connection item : Connection.values()) {
            map = new HashMap<>();
            map.put("name", item.getName());
            map.put("value", item.getValue());
            result.add(map);
        }
        return result;
    }

    @RequestMapping(value = "os", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> os() {

        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> map = null;
        for (OperationSystem item : OperationSystem.values()) {
            map = new HashMap<>();
            map.put("name", item.getName());
            map.put("value", item.getValue());
            result.add(map);
        }
        return result;
    }

    @RequestMapping(value = "telcom", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> telcom() {

        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> map = null;
        for (Operator item : Operator.values()) {
            map = new HashMap<>();
            map.put("name", item.getName());
            map.put("value", item.getValue());
            result.add(map);
        }
        return result;
    }

    @RequestMapping(value = "education", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> education() {

        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> map = null;
        for (Education item : Education.values()) {
            map = new HashMap<>();
            map.put("name", item.getName());
            map.put("value", item.getValue());
            result.add(map);
        }
        return result;
    }

    /**
     * 婚恋状态
     * @return
     */
    @RequestMapping(value = "marriage", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> marriage() {

        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> map = null;
        for (MarriageStatus item : MarriageStatus.values()) {
            map = new HashMap<>();
            map.put("name", item.getName());
            map.put("value", item.getValue());
            result.add(map);
        }
        return result;
    }

    @RequestMapping(value = "500", method = RequestMethod.GET)
    public String serverError() {

        return "default/500";
    }

    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String pageNotFount() {

        return "default/404";
    }

}
