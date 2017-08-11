package com.falcon.dsp.common;

import com.falcon.dsp.enumration.SpeedMode;
import com.falcon.dsp.rest.campaign.request.GdtCampaign;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/24.
 */
public class JsonUtil {
    private static ObjectMapper MAPPER;

    private static ObjectMapper multiMapper;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MAPPER.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);

        multiMapper = new ObjectMapper();
        multiMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        multiMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        multiMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        multiMapper.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
    }

    /**
     * java对象转换成json
     */
    public static <T> String toJson(T pojo) {
        String result;
        try {
            result = MAPPER.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json转换异常", e);
        }
        return result;
    }

    /**
     * json转换成Java对象
     */
    public static <T> T fromJson(String json, Class<T> valueType) {
        T t;
        try {
            t = MAPPER.readValue(json, valueType);
        } catch (IOException e) {
            throw new RuntimeException("json转换异常", e);
        }
        return t;
    }

    public static <T> T mapToObject(Map map, Class<T> valueType) {
        T t;
        try {
            String result = MAPPER.writeValueAsString(map);
            t = MAPPER.readValue(result, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json转换异常",e);
        } catch (IOException e) {
            throw new RuntimeException("json转换异常",e);
        }

        return t;
    }

    public static <T> T multiValueMapToObject(MultiValueMap valueMap, Class<T> valueType) {
        T t;
        try {
            String result = multiMapper.writeValueAsString(valueMap);
            t = multiMapper.readValue(result, valueType);
        } catch (Exception e) {
            throw new RuntimeException("json转换异常");
        }

        return t;
    }

    public static Map objectToMap(Object object) {
        Map map;
        try {
            String result = MAPPER.writeValueAsString(object);
            map = MAPPER.readValue(result, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json转换异常");
        } catch (IOException e) {
            throw new RuntimeException("json转换异常");
        }

        return map;
    }


    public static void main(String[] args) {

        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        GdtCampaign gdtCampaign = new GdtCampaign();

        gdtCampaign.setDayBudget(2346);
        gdtCampaign.setSpeedMode(SpeedMode.SPEEDMODE_NORMAL);
        try {
            System.out.println(MAPPER.writeValueAsString(gdtCampaign));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
