package com.livingguide.common.po;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class BasePO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
