package com.falcon.dsp.controller;

import com.falcon.dsp.jdbc.model.AreaModel;
import com.falcon.dsp.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 公共
 * @author dongbin.yu
 * @from 2016-04-12
 * @since V1.0
 */
@Controller
@RequestMapping("/target")
public class TargetController {

    @Autowired
    private TargetService service;

    @RequestMapping(value = "/area", method = RequestMethod.GET)
    @ResponseBody
    public List<AreaModel> areaList(String searchKey) {

        return service.areaList(searchKey);
    }

    @RequestMapping(value = "/industry", method = RequestMethod.GET)
    @ResponseBody
    public List<AreaModel> industryList(String searchKey) {

        return service.industryList(searchKey);
    }

    @RequestMapping(value = "/interest", method = RequestMethod.GET)
    @ResponseBody
    public List<AreaModel> interest(String searchKey) {

        return service.interestList(searchKey);
    }
}
