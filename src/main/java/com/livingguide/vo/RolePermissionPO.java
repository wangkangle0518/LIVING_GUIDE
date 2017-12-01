package com.livingguide.vo;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.livingguide.common.po.BasePO;

public class RolePermissionPO extends BasePO{
		
	//columns START
    /**
     * 角色ID    db_column: rid 
     */
	private java.lang.Long rid;
    /**
     * 权限ID    db_column: pid 
     */
	private java.lang.Long pid;
	//columns END

	public void setRid(java.lang.Long value) {
		this.rid = value;
	}
	
	public java.lang.Long getRid() {
		return this.rid;
	}
	public void setPid(java.lang.Long value) {
		this.pid = value;
	}
	
	public java.lang.Long getPid() {
		return this.pid;
	}

	public boolean equals(Object obj) {
		if(obj instanceof RolePermissionPO == false) return false;
		if(this == obj) return true;
		RolePermissionPO other = (RolePermissionPO)obj;
		return new EqualsBuilder()
			.append(getRid(),other.getRid())
			.append(getPid(),other.getPid())
			.isEquals();
	}
}

