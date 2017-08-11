package com.falcon.dsp.rest;

import com.falcon.dsp.common.GdtAction;
import com.falcon.dsp.enumration.CampaignType;
import com.falcon.dsp.enumration.SpeedMode;
import com.falcon.dsp.enumration.target.*;
import com.falcon.dsp.rest.campaign.request.GdtCampaign;
import com.falcon.dsp.rest.campaign.response.GdtCampaignResult;
import com.falcon.dsp.rest.campaign.response.GdtCampaignTableResult;
import com.falcon.dsp.rest.strategy.request.GdtStrategy;
import com.falcon.dsp.rest.strategy.response.GdtStrategyResult;
import com.falcon.dsp.rest.target.request.GdtTarget;
import com.falcon.dsp.rest.target.response.GdtTargetResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongbin.yu
 * @from 2016-04-01
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class GdtRestHandlerTest {

    @Autowired
    private GdtRestHandler handler;

    @Test
    public void testGet() throws Exception {


        Map map = new HashMap();
        map.put("uid", 51967);
        map.put("page", 1);
        map.put("page_size", 20);
        GdtCampaignTableResult campaignTableResult = handler.get("campaign/select", GdtCampaignTableResult.class, map);

        System.out.println();
    }

    @Test
    public void testPost() throws Exception {

        GdtCampaign campaign = new GdtCampaign();
        campaign.setUid(51967);
        campaign.setCname("test create campaign success");
        campaign.setCtype(CampaignType.CAMPAIGNTYPE_DISPLAY);
        campaign.setDayBudget(10000);
        campaign.setSpeedMode(SpeedMode.SPEEDMODE_NORMAL);

        GdtCampaignResult campaignResult = handler.post("campaign/create", campaign, GdtCampaignResult.class, null);
        System.out.println();
    }

    @Test
    public void testTarget() {

        GdtTarget target = new GdtTarget();
        target.setUid(51967);
        target.setDescription("rest test description ydb2");
        target.setGender(new Gender[]{Gender.FEMALE});
        target.setConnection(new Connection[]{Connection.NET2G, Connection.NET3G, Connection.NET4G, Connection.WIFI});
        target.setEducation(new Education[]{Education.DOCTOR, Education.JUNIOR});
        target.setOs(new OperationSystem[]{OperationSystem.ANDROID, OperationSystem.IOS});
        target.setTelcom(new Operator[]{Operator.CMC, Operator.CTC, Operator.CUC});
        target.setArea(new Integer[]{110000, 120000, 130000});
        target.setAge(new String[]{"\"10~19\""});


        target.setMname("rest target test ydb2");

        GdtTargetResult targetResult = handler.post("ad_target/create", target, GdtTargetResult.class, null);

    }

    @Test
    public void testTargetGet() {
        Map param = new HashMap();
        param.put("mid", 86577);
        param.put("uid",51967);
        GdtTargetResult gdtTargetResult = handler.get("ad_target/read", GdtTargetResult.class, param);
    }

    @Test
    public void test3(){
        Map param = new HashMap();
        param.put("uid",146907);
        GdtCampaignResult gdtCampaignResult = handler.get("campaign/select", GdtCampaignResult.class, param);
        System.out.printf(gdtCampaignResult.toString());
    }

    @Test
    public void test4(){
        GdtStrategy gdtStrategy = new GdtStrategy();
        gdtStrategy.setUid(51967);
        gdtStrategy.setAid(91567);
        GdtStrategyResult deleteResult = handler.post(GdtAction.AD_GROUP_DELETE, gdtStrategy, GdtStrategyResult.class, null);
        System.out.printf(deleteResult.toString());
    }
}