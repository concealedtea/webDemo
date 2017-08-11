package com.falcon.dsp.common;

import com.falcon.dsp.rest.GdtRestHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author dongbin.yu
 * @from 2016-04-09
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AreaHandlerTest {

    @Autowired
    private GdtRestHandler handler;

    @Test
    public void create() {

        Map map = handler.get("/utilities/area_list", Map.class, null);
        Map data = (Map) map.get("data");
        ArrayList<Map> list = (ArrayList<Map>) data.get("list");

        for (Map area : list) {
            int id = (int)area.get("id");
            if (id / 10000 == 99) {
                continue;
            }

            if (id % 10000 == 0) {
                area.put("parent_id", 0);
            } else {
                area.put("parent_id", (id / 10000) * 10000);
            }
        }

        String sql = "insert into area(id,name,parent_id) values(%d,\"%s\",%d);";
        for (Map area : list) {
            String name = (String)area.get("name");
            if(name.contains("未知")){
                continue;
            }

            if (area.get("parent_id") == null) {
                continue;
            }

            System.out.println(String.format(sql,area.get("id"),area.get("name"),area.get("parent_id")));
        }


    }

}
