package com.falcon.dsp.service;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.common.GdtAction;
import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.enumration.CostType;
import com.falcon.dsp.enumration.target.Education;
import com.falcon.dsp.enumration.target.Gender;
import com.falcon.dsp.enumration.target.OperationSystem;
import com.falcon.dsp.enumration.target.Operator;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.entity.Report;
import com.falcon.dsp.jdbc.entity.Strategy;
import com.falcon.dsp.param.StrategyParam;
import com.falcon.dsp.param.TargetParam;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.strategy.request.GdtStrategy;
import com.falcon.dsp.rest.strategy.response.GdtUtilityResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by falcon-zhangxg on 2016/4/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class StrategyServiceTest {

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlSessionHandler;

    @Resource(name = "reportSqlHandler")
    private SqlSessionHandler reportSqlSessionHandler;

    @Autowired
    private GdtRestHandler gdtRestHandler;

    @Autowired
    private StragegyService strategyService;

    @Autowired
    private AdvertiserService advertiserService;
    @Test
    public void test() {
        PageParameter pageParameter = new PageParameter();
        pageParameter.setPageSize(10);
        pageParameter.setCurrentPage(1);
        List<Strategy> result = sqlSessionHandler.selectList("strategyMapper.strategy:list:page", null, pageParameter);
        System.out.printf(result.toString());
    }

    @Test
    public void test1() {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", 1164220);
        params.put("sid", 13919859);
        params.put("settledTime", DateUtils.stringtoDate("2016-04-03", "yyyy-MM-dd"));
        Report result = reportSqlSessionHandler.selectOne("rptStrategyDaily.rpt:strategy:daily:select:day", params);
        System.out.printf(result.toString());
    }

    @Test
    public void test2() {
        TargetParam targetParam = new TargetParam();

        targetParam.setArea(new HashMap<Integer, String>() {
            {
                put(1, "北京");
                put(2, "上海");
            }
        });

        targetParam.setAge("1∞3");
        targetParam.setEducation(new HashMap<Education, String>() {{
            put(Education.DOCTOR, "医生");
            put(Education.UNKNOWN, "未知");
        }});
        targetParam.setGender(new HashMap<Gender, String>() {{
            put(Gender.FEMALE, "女");
        }});

        targetParam.setHours(new Integer[][]{
                new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        });

        targetParam.setOs(new HashMap<OperationSystem, String>() {{
            put(OperationSystem.ANDROID, "安卓");
            put(OperationSystem.IOS, "ios");
        }});
        targetParam.setTelcom(new HashMap<Operator, String>() {{
            put(Operator.CMC, "cmc");
            put(Operator.CTC, "ctc");
        }});

        StrategyParam a = new StrategyParam();
        a.setBid(0.35);
        a.setCreativeId(1);
        a.setDescription("描述");
        a.setEnd(new Date());
        a.setStart(new Date());
        a.setName("name");
        a.setSearchKey("searchkey");
        a.setTargetParam(targetParam);
        System.out.println(JsonUtil.toJson(a));
    }

    @Test
    public void test3() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", "51967");
        Map<String, Object> result = gdtRestHandler.get(GdtAction.AD_GROUP_SELECT, Map.class, map);
        for (Object entry : ((List) ((Map) result.get("data")).get("list"))) {
            System.out.println("begin =============");
            for (Map.Entry<String, Object> item : ((Map<String, Object>) entry).entrySet()) {
                System.out.println(String.format(item.getKey() + ":" + item.getValue()));
            }
            System.out.println("end =============");
        }
        System.out.printf(result.toString());
    }

    @Test
    public void test4() {

        TargetParam targetParam = new TargetParam();
        targetParam.setAge("10~20");
        targetParam.setArea(new HashMap<Integer, String>() {{
            put(710000, "北京");
        }});
        targetParam.setGender(new HashMap<Gender, String>() {{
            put(Gender.FEMALE, "女");
        }});

        StrategyParam strategyParam = new StrategyParam();
        strategyParam.setBid(0.35);
        strategyParam.setTargetParam(targetParam);
        strategyParam.setCostType(CostType.COSTTYPE_CPC);

        GdtStrategy gdtStrategy = strategyParam.fetchGdtStrategyModel();
        gdtStrategy.setSiteset(new String[]{"SITESET_QZONE"});

        Map<String, Object> map = new HashMap<>();
        map.put("ad_target", "");
        map.put("uid", "");
        map.put("ad_group", "");
        map.put("creative", "");

        GdtUtilityResult gdtUtilityResult = gdtRestHandler.post(GdtAction.UTILITIES_ESTIMATE, map, GdtUtilityResult.class, null);
    }

    @Test
    public void test5() {
        Map<String, Object> map = new HashMap<>();
        map.put("campaign_id", 184425);
        List<HashMap> result = sqlSessionHandler.selectList("strategyMapper.strategy:id:list", map);
        System.out.println(result.toString());
    }


    @Test
    public void test6(){
        //4294938496
        int uid = 51967;
        int cid = 136651;
        //long s = 4294938496;

        strategyService.syncCampaign(uid,cid+"");
    }

    @Test
    public void testSync(){
        int agencyId = 1;
        int[] uidArray = new int[]{1549483};
        //int[] uidArray = new int[]{1407814,1427998,1449256,1549483,1559000,1574161};
        for (int uid : uidArray) {
          /*  GdtAdvertiserResult result = advertiserService.getGdtAdvertiserByUid(uid, null);
            GdtAdvertiser ga = result.getData();
            ga.setAgencyId(agencyId);
            ga.setUid(uid);
            advertiserService.insertAdvertiser(ga);*/
            strategyService.syncCampaignByUid(uid);
        }

    }

    @Test
    public void testDate(){
        int startTime = 1457625600;
        int endTime = 1459439999;
        Date start = DateUtils.formatIntToDate(startTime);
        Date end =  DateUtils.formatIntToDate(endTime);
        long nowTime = new Date().getTime();
        Date now =  DateUtils.formatIntToDate((int) (nowTime/1000));
        System.out.println(nowTime);
    }

}

