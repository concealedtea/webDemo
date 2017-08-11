package com.falcon.dsp.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author dongbin.yu
 * @from 2016-04-19
 * @since V1.0
 */
public class IndustryTest {

    private String sql = "insert into industry_prod(id,name,parent_id) values(%d,\"%s\",%d);";

    @Test
    public void testHandleIndustry() {

        InputStream inputStream = this.getClass().getResourceAsStream("/industry.json");

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(inputStream,Map.class);
            Map data = (Map)map.get("data");
            List<Map> list = (List) data.get("list");
            childListParse(list, 0);

            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void childListParse(List<Map> list,long parent_id) {
        if (list != null) {
            for (Map parent : list) {
                long id = (long) parent.get("id");
                String name = (String) parent.get("name");
                long parentId = Long.parseLong(parent.get("parent_id").toString());


                System.out.println(String.format(sql, id, name, parentId));

            }
        }
    }

}
