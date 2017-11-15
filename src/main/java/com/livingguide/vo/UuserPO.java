package com.livingguide.vo;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.livingguide.common.po.BasePO;
import com.livingguide.util.DateUtil;

public class UuserPO extends BasePO{
		
	//columns START
    /**
     * id    db_column: id 
     */
	private java.lang.Long id;
    /**
     * 用户昵称    db_column: nickname 
     */
	private java.lang.String nickname;
    /**
     * 邮箱|登录帐号    db_column: email 
     */
	private java.lang.String email;
    /**
     * 密码    db_column: pswd 
     */
	private java.lang.String pswd;
    /**
     * 创建时间    db_column: create_time 
     */
	private java.util.Date createTime;
    /**
     * 最后登录时间    db_column: last_login_time 
     */
	private java.util.Date lastLoginTime;
    /**
     * 1:有效，0:禁止登录    db_column: status 
     */
	private java.lang.Long status;
	//columns END

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setNickname(java.lang.String value) {
		this.nickname = value;
	}
	
	public java.lang.String getNickname() {
		return this.nickname;
	}
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	
	public java.lang.String getEmail() {
		return this.email;
	}
	public void setPswd(java.lang.String value) {
		this.pswd = value;
	}
	
	public java.lang.String getPswd() {
		return this.pswd;
	}
	public String getCreateTimeString() {
		return DateUtil.format(getCreateTime(), DateUtil.FORMAT_DATETIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateUtil.parse(value, DateUtil.FORMAT_DATETIME));
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public String getLastLoginTimeString() {
		return DateUtil.format(getLastLoginTime(), DateUtil.FORMAT_DATETIME);
	}
	public void setLastLoginTimeString(String value) {
		setLastLoginTime(DateUtil.parse(value, DateUtil.FORMAT_DATETIME));
	}
	
	public void setLastLoginTime(java.util.Date value) {
		this.lastLoginTime = value;
	}
	
	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	public void setStatus(java.lang.Long value) {
		this.status = value;
	}
	
	public java.lang.Long getStatus() {
		return this.status;
	}

	public boolean equals(Object obj) {
		if(obj instanceof UuserPO == false) return false;
		if(this == obj) return true;
		UuserPO other = (UuserPO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

