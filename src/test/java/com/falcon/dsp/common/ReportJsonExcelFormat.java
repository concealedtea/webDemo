package com.falcon.dsp.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author dongbin.yu
 * @from 2016-04-25
 * @since V1.0
 */
public class ReportJsonExcelFormat {

    @Test
    public void jsonToExcel(){

        InputStream inputStream = this.getClass().getResourceAsStream("/report.json");

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(inputStream,Map.class);
            Map data = (Map)map.get("data");
            List<Map<String,Object>> list = (List) data.get("list");
            boolean init = true;
            for (Map<String,Object> reportMap : list) {
                if (init) {
                    for (Map.Entry entry : reportMap.entrySet()) {
                        System.out.print(entry.getKey() + "\t");
                    }
                    init = false;
                    System.out.println();
                }

                for (Map.Entry entry : reportMap.entrySet()) {
                    System.out.print(entry.getValue() + "\t");
                }
                System.out.println();
            }
            
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
