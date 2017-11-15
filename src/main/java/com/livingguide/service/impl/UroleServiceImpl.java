package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.UroleDAO;
import com.livingguide.service.UroleService;
import com.livingguide.vo.UrolePO;
import com.livingguide.vo.query.UroleQueryModel;

@Service
public class UroleServiceImpl extends BaseService<UrolePO, UroleQueryModel> implements UroleService {

	//使用DAO时放开注释即可
	//private UroleDAO uroleDAO;
	@Autowired
	public void setDAO(UroleDAO uroleDAO){
		super.setDAO(uroleDAO);
		//this.uroleDAO = uroleDAO;
	}
}
