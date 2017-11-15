package com.livingguide.vo;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.livingguide.common.po.BasePO;

public class UrolePO extends BasePO{
		
	//columns START
    /**
     * id    db_column: id 
     */
	private java.lang.Long id;
    /**
     * 角色名称    db_column: name 
     */
	private java.lang.String name;
    /**
     * 角色类型    db_column: type 
     */
	private java.lang.String type;
	//columns END

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	public java.lang.String getType() {
		return this.type;
	}

	public boolean equals(Object obj) {
		if(obj instanceof UrolePO == false) return false;
		if(this == obj) return true;
		UrolePO other = (UrolePO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

