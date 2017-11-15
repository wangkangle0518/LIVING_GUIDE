package com.livingguide.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 日志功能组件
 *
 */
public abstract class BComponent {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 获取日志功能组件
	 * 
	 * @return logger
	 */
	protected Logger getLogger(){
		return logger;
	}
}
