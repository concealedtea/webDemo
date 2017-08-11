package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.enumration.UserRoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * 用于代理商下的用户管理(管理该代理商下的广告主或者代理商登录账户)
 *
 * @author yujuan.zhang
 * @from 2016-07-08
 * @since V3.2
 */
@Alias("userListModel")
public class UserListModel {

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
     * 用户角色
     */
    @JsonProperty(value = "user_role")
    private UserRoleType userRole;

    /**
     * 广告主名称
     */
    private String uname;

    /**
     * 对应的广告主id或者代理商id
     */
    private Integer uid;

    /**
     * 用户状态
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

    public UserRoleType getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleType userRole) {
        this.userRole = userRole;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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
}
