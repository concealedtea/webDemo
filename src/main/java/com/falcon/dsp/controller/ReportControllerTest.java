package com.falcon.dsp.controller;

import com.falcon.dsp.annotation.UserAttribute;
import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.enumration.DateType;
import com.falcon.dsp.enumration.ReportType;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.exception.FalconDspServerException;
import com.falcon.dsp.handler.RoleAndView;
import com.falcon.dsp.jdbc.entity.Advertiser;
import com.falcon.dsp.jdbc.entity.Campaign;
import com.falcon.dsp.jdbc.entity.Report;
import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.jdbc.model.*;
import com.falcon.dsp.param.ReportParam;
import com.falcon.dsp.rest.campaign.params.CampaignParam;
import com.falcon.dsp.service.AdvertiserService;
import com.falcon.dsp.service.CampaignService;
import com.falcon.dsp.service.ReportService;
import com.falcon.dsp.service.ReportServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author dongbin.yu
 * @from 2016-04-01
 * @since V1.0
 */
@Controller
@RequestMapping("/report/test")
public class ReportControllerTest {

    @Autowired
    private ReportServiceTest reportServiceTest;

    @Autowired
    AdvertiserService advertiserService;
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public RoleAndView view() {
        UserModel model = new UserModel();
        model.setInLieyingAccount(true);
        model.setUserName("sssd");
        model.setUserRole(UserRoleType.ADMIN);
        return new RoleAndView("test", model);
    }

    @RequestMapping(value = "data", method = RequestMethod.GET)
    @ResponseBody
    public List<PhoneModel> userReport() {
        List<PhoneModel> modelList = reportServiceTest.phoneTotals(new PhoneModel());
        return modelList;
    }


}
