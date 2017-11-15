package com.livingguide.vo;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.livingguide.common.po.BasePO;

public class RegisterPO extends BasePO{
		
	//columns START
    /**
     * 用户ID    db_column: USER_ID 
     */
	private java.lang.Integer userId;
    /**
     * 用户名    db_column: USER_NAME 
     */
	private java.lang.String userName;
    /**
     * 密码    db_column: PASSWORD 
     */
	private java.lang.String password;
    /**
     * 版本号    db_column: VERSION 
     */
	private java.lang.Integer version;
	//columns END

	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setUserName(java.lang.String value) {
		this.userName = value;
	}
	
	public java.lang.String getUserName() {
		return this.userName;
	}
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setVersion(java.lang.Integer value) {
		this.version = value;
	}
	
	public java.lang.Integer getVersion() {
		return this.version;
	}

	public boolean equals(Object obj) {
		if(obj instanceof RegisterPO == false) return false;
		if(this == obj) return true;
		RegisterPO other = (RegisterPO)obj;
		return new EqualsBuilder()
			.append(getUserId(),other.getUserId())
			.isEquals();
	}
}

