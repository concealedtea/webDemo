package com.falcon.dsp.jdbc.entity;

import com.alibaba.druid.util.StringUtils;
import com.falcon.dsp.exception.FalconDspServerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 访问权限控制参数
 * Created by ZhouChunhui
 * on 2016/5/31 0031.
 */
public class AccessParam {
    //key  请求的参数，如果不包含的话，不验证，value 该参数可以包含的值
    private Map<String,List<Object>> params = new HashMap<>();

    public Map<String, List<Object>> getParams() {
        return params;
    }

    public void setParams(Map<String, List<Object>> params) {
        this.params = params;
    }

    public void put(String key,List list){
        this.params.put(key,list);
    }

    public void addValue(String key,Object value){
        List list = this.params.get(key);
        if(list==null){
            list = new ArrayList();
        }
        list.add(value);
    }
    public boolean ifHasPermissions(Map<String,String[]> map){
        List<Object> list = null;
        for (String str : map.keySet()) {
           list = params.get(str);
           if(list==null){
                continue;
            }else{
               String[] values = map.get(str);
               for (String value : values) {
                   Integer val = StringUtils.stringToInteger(value);
                   if(val==null){
                       if(!list.contains(value)){
                           throw new FalconDspServerException("没有访问该资源的权限！");
                       }
                   }else{
                       if(!list.contains(val)){
                           throw new FalconDspServerException("没有访问该资源的权限！");
                       }
                   }

               }
           }
        }
        return true;
    }
}
