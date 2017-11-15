package com.livingguide.common.po;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class BasePO {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
