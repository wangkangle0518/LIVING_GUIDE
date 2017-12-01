package com.livingguide.vo;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.livingguide.common.po.BasePO;

public class PermissionPO extends BasePO{
		
	//columns START
    /**
     * id    db_column: id 
     */
	private java.lang.Long id;
    /**
     * url地址    db_column: url 
     */
	private java.lang.String url;
    /**
     * url描述    db_column: name 
     */
	private java.lang.String name;
	//columns END

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}

	public boolean equals(Object obj) {
		if(obj instanceof PermissionPO == false) return false;
		if(this == obj) return true;
		PermissionPO other = (PermissionPO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

