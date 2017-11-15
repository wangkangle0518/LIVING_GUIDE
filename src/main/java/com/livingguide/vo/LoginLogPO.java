package com.livingguide.vo;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.livingguide.common.po.BasePO;
import com.livingguide.util.DateUtil;

public class LoginLogPO extends BasePO{
		
	//columns START
    /**
     * 日志ID    db_column: LOG_ID 
     */
	private java.lang.Long logId;
    /**
     * 用户ID    db_column: USER_ID 
     */
	private java.lang.Long userId;
    /**
     * 登录IP    db_column: IP 
     */
	private java.lang.String ip;
    /**
     * 登录时间    db_column: LOGIN_DATETIME 
     */
	private java.util.Date loginDatetime;
	//columns END

	public void setLogId(java.lang.Long value) {
		this.logId = value;
	}
	
	public java.lang.Long getLogId() {
		return this.logId;
	}
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setIp(java.lang.String value) {
		this.ip = value;
	}
	
	public java.lang.String getIp() {
		return this.ip;
	}
	public String getLoginDatetimeString() {
		return DateUtil.format(getLoginDatetime(), DateUtil.FORMAT_DATETIME);
	}
	public void setLoginDatetimeString(String value) {
		setLoginDatetime(DateUtil.parse(value, DateUtil.FORMAT_DATETIME));
	}
	
	public void setLoginDatetime(java.util.Date value) {
		this.loginDatetime = value;
	}
	
	public java.util.Date getLoginDatetime() {
		return this.loginDatetime;
	}

	public boolean equals(Object obj) {
		if(obj instanceof LoginLogPO == false) return false;
		if(this == obj) return true;
		LoginLogPO other = (LoginLogPO)obj;
		return new EqualsBuilder()
			.append(getLogId(),other.getLogId())
			.isEquals();
	}
}

