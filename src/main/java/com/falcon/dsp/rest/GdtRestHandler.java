package com.falcon.dsp.rest;

import com.falcon.dsp.common.EncodeUtil;
import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.exception.FalDspException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongbin.yu
 * @from 2016-03-29
 * @since V1.0
 */
public class GdtRestHandler {

    private String urlPrefix;
    private String uid;
    private String appId;
    private String appKey;
    private String contentType;

    private RestTemplate restTemplate;

    public <T> T get(String url, Class<T> classType, Map param) {

        String token = generateToken();
        if (param == null) {
            param = new HashMap();
        }
        param.put("token", token);

        String realUrl = generateUrl(url, param);

        Map map = restTemplate.getForObject(realUrl, Map.class, param);
        if ((int) map.get("ret") != 0) {
            throw new FalDspException((int)map.get("ret"), (String)map.get("msg"));
        }

        return JsonUtil.mapToObject(map, classType);
    }

    public <T> T post(String url, Object requestObject, Class<T> classType, Map param) {

        String token = generateToken();
        if (param == null) {
            param = new HashMap();
        }
        param.put("token", token);

        //设置mediaType
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType(contentType);
        headers.setContentType(type);

        Map requestMap = null;
        if (requestObject instanceof Map) {
            requestMap = (Map) requestObject;
        } else {
            requestMap = JsonUtil.objectToMap(requestObject);
        }

        HttpEntity entity = new HttpEntity(generateParam(requestMap), headers);

        String realUrl = generateUrl(url,param);
        Map map = restTemplate.postForObject(realUrl, entity, Map.class, param);
        if ((int) map.get("ret") != 0) {
            throw new FalDspException((int)map.get("ret"), (String)map.get("msg"));
        }

        return JsonUtil.mapToObject(map, classType);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    private String generateUrl(String url) {
        if (url.startsWith("/")) {
            return urlPrefix + url;
        } else {
            return urlPrefix + "/" + url;
        }
    }

    private String generateUrl(String url, Map map) {
        if (map != null && map.size() > 0) {
            List<String> params = new ArrayList<String>();
            for (Object item : map.keySet()) {
                params.add(String.format("%s={%s}", item.toString(), item.toString()));
            }
            url = url + "?" + StringUtils.join(params, "&");
        }
        return this.generateUrl(url);
    }

    private String generateParam(Map<String,Object> map) {
        List<String> params = new ArrayList<String>();
        if (map != null && map.size() > 0) {
            for (Map.Entry entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    params.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
                } else {
                    params.add(String.format("%s=%s", entry.getKey(), JsonUtil.toJson(entry.getValue())));
                }
            }
        }
        return StringUtils.join(params, "&");
    }

    private String generateToken() {
        long time = System.currentTimeMillis() / 1000;
        String sign = EncodeUtil.SHA1(appId + appKey + time);
        return EncodeUtil.getBase64(uid + "," + appId + "," + time + "," + sign);
    }

    public String getUid() {
        return uid;
    }
}
