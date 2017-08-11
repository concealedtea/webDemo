package com.falcon.dsp.param;

import com.falcon.dsp.enumration.UserRoleType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用于代理商下的用户管理(管理该代理商下的广告主或者代理商登录账户)
 *
 * @author yujuan.zhang
 * @from 2016-07-07
 * @since V1.2
 */
public class UserParam {

    /**
     * 系统标识
     */
    private Integer id;

    /**
     * 用户名
     */
    @JsonProperty(value = "user_name")
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户角色
     */
    @JsonProperty(value = "user_role")
    private UserRoleType userRole;

    /**
     * 对应的广告主id或者代理商id
     */
    private Integer uid;

    /**
     * 用户状态
     * "
     */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleType getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleType userRole) {
        this.userRole = userRole;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserInfoParam{id=" + id + ", userName=" + userName + ", password=" + password + ", userRole=" + userRole
                + ", uid=" + uid + "}";
    }
}
