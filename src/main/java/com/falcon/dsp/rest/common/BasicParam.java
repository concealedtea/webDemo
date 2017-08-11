package com.falcon.dsp.rest.common;

import com.falcon.dsp.common.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public class BasicParam {

    private String orderby;
    private String orderbyColumn;

    @JsonProperty
    private List<String> fields;

    @JsonProperty
    private Map<String, Object> where;

    private Integer page;
    private Integer pageSize;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public void addFields(String field) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.add(field);
    }

    public Map<String, Object> getWhere() {
        return where;
    }

    public void setWhere(Map<String, Object> where) {
        this.where = where;
    }

    public void addWhereCondition(String key, Object value) {
        if (this.where == null) {
            where = new HashMap<>();
        }
        this.where.put(key, value);
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getOrderbyColumn() {
        return orderbyColumn;
    }

    public void setOrderbyColumn(String orderbyColumn) {
        this.orderbyColumn = orderbyColumn;
    }

    public Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            getParams(getClass(), params, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return params;
    }

    private Object invokeGetMethod(Class<?> clazz, String fieldName) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, clazz);
        Method getMethod = propertyDescriptor.getReadMethod();
        getMethod.setAccessible(true);
        return getMethod.invoke(this);
    }

    private String transferUnderline(String str) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
        }
        if (builder.charAt(0) == '_') {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * 获取类实例的属性值
     *
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Map<String, Object> getParams(Class<?> clazz, Map<String, Object> params, boolean includeParent) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = invokeGetMethod(clazz, fieldName);
            String paramName = transferUnderline(fieldName);
            JsonProperty meta = field.getAnnotation(JsonProperty.class);
            if (field.getType().isArray() && fieldValue != null) {
                List<Object> value = new ArrayList<Object>();
                for (Object tmp : (Object[]) fieldValue) {
                    value.add(tmp);
                }
                params.put(paramName, value);
            } else {
                if (fieldValue != null) {
                    params.put(paramName, fieldValue);
                }
            }
            if (meta != null && fieldValue != null) {
                if (field.getType() == List.class || field.getType() == Map.class) {
                    params.put(paramName, JsonUtil.toJson(fieldValue));
                }
            }
        }
        return params;
    }
}
