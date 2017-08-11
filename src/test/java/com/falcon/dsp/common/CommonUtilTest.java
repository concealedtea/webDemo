package com.falcon.dsp.common;

import com.falcon.dsp.jdbc.entity.Report;
import com.falcon.dsp.jdbc.model.IndexModel;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.common.GdtBaseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by Administrator on 2016/3/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CommonUtilTest {

    @Autowired
    private GdtRestHandler gdtRestHandler;


    public void testUitl() {

    }

    @Test
    public void testToken() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", "1164220");
        map.put("time_range", "{\"start_date\":\"2016-04-01\",\"end_date\":\"2016-04-08\"}");
//        map.put("page","1");
//        map.put("page_size","10");
//        map.put("group_by","{\"date\"}");
        gdtRestHandler.get("user_rpt/select", GdtBaseResult.class, map);
    }

    @Test
    public void test() {
        Integer[][] targetTime = new Integer[][]{
                new Integer[]{1, 1, 1, 0},
                new Integer[]{1, 1, 1, 0},
                new Integer[]{1, 1, 1, 0},
                new Integer[]{1, 1, 1, 0},
                new Integer[]{1, 1, 1, 0},
                new Integer[]{1, 1, 1, 0},
                new Integer[]{1, 1, 1, 0},
        };
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer[] day : targetTime) {
            for (Integer hour : day) {
                stringBuilder.append(String.format("%d%d", hour, hour));
            }
        }
        System.out.println(stringBuilder.toString());
    }

    @Test
    public void test1() {
        IndexModel indexModel = new IndexModel();
        Report totalReport = new Report();
        totalReport.setViewCount(12312313L);
        totalReport.setCost(987.36);
        totalReport.setClickCount(12547L);

        List<Report> list = new ArrayList<>();
        list.add(new Report() {{
            this.setSettledTime(new Date());
            this.setViewCount(123123);
            this.setClickCount(1231);
            this.setCost(59.23);
        }});
        List<Report> genders = new ArrayList<Report>() {
            {
                add(new Report() {{
                    this.setGenderId(1);
                    this.setGenderName("男");
                    this.setViewCount(123L);
                }});
            }
        };
        List<Report> ages = new ArrayList<Report>() {{
            add(new Report() {{
                this.setAges(2);
                this.setAgeName("21~25");
                this.setViewCount(1234L);
            }});
        }};
        List<Report> area = new ArrayList<Report>() {{
            add(new Report() {{
                this.setStateId(1);
                this.setStateName("北京");
                this.setViewCount(1231L);
            }});
        }};
        indexModel.init(totalReport,list, genders, area, ages, false);
        System.out.println(JsonUtil.toJson(indexModel));

    }

//    @Test
//    public void test2() {
//        System.out.println(Double.parseDouble(MathUtil.doubleFormat(45.893)));
//    }
}
