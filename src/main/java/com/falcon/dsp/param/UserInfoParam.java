package com.falcon.dsp.param;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wang.wei
 * @from 2016-04-15
 * @since V1.0
 */
public class UserInfoParam extends BaseParam {

	private Integer id;
	/**
	 * 用户名
	 */
	@JsonProperty(value = "user_name")
	private String userName;

	/**
	 * 密码
	 * 
	 */
	private String password;
	/**
	 * 用户状态
	 * "
	 */
	private Integer status;

	/**
	 * 用户角色
	 * 
	 */
	@JsonProperty(value = "user_role")
	private String userRole;

	/**
	 * 广告主 Id
	 */
	private Integer uid;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
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
		return "UserInfoParam{id=" + id + ", userName=" + userName + ", password=" + password + ", userRole=" + userRole
				+ ", uid=" + uid + "}";
	}
}
