package com.falcon.dsp.jdbc.entity;

import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.rest.common.BasicEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * 用户信息表
 */
@Alias("userInfo")
public class UserInfo extends BasicEntity {

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
     * 广告主 Id
     */
    private Integer uid;


    private int status;


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

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                ", uid=" + uid +
                ", status=" + getStatus() +
                '}';
    }
}
