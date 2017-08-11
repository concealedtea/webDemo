package com.falcon.dsp.handler;

import com.falcon.dsp.jdbc.entity.UserInfo;

/**
 * @author dongbin.yu
 * @from 2016-04-21
 * @since V1.0
 * 将跳转地址和用户角色包装起来
 */
public class RoleAndView {

    private String view;

    private UserInfo userInfo;

    public RoleAndView(String view, UserInfo userInfo) {
        this.view = view;
        this.userInfo = userInfo;
    }

    public String getView() {
        return view;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
