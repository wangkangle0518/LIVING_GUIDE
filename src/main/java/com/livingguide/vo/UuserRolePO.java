package com.livingguide.vo;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.livingguide.common.po.BasePO;

public class UuserRolePO extends BasePO{
		
	//columns START
    /**
     * 用户ID    db_column: uid 
     */
	private java.lang.Long uid;
    /**
     * 角色ID    db_column: rid 
     */
	private java.lang.Long rid;
	//columns END

	public void setUid(java.lang.Long value) {
		this.uid = value;
	}
	
	public java.lang.Long getUid() {
		return this.uid;
	}
	public void setRid(java.lang.Long value) {
		this.rid = value;
	}
	
	public java.lang.Long getRid() {
		return this.rid;
	}

	public boolean equals(Object obj) {
		if(obj instanceof UuserRolePO == false) return false;
		if(this == obj) return true;
		UuserRolePO other = (UuserRolePO)obj;
		return new EqualsBuilder()
			.append(getUid(),other.getUid())
			.isEquals();
	}
}

