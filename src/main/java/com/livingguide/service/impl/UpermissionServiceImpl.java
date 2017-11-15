package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.UpermissionDAO;
import com.livingguide.service.UpermissionService;
import com.livingguide.vo.UpermissionPO;
import com.livingguide.vo.query.UpermissionQueryModel;

@Service
public class UpermissionServiceImpl extends BaseService<UpermissionPO, UpermissionQueryModel> implements UpermissionService {

	//使用DAO时放开注释即可
	//private UpermissionDAO upermissionDAO;
	@Autowired
	public void setDAO(UpermissionDAO upermissionDAO){
		super.setDAO(upermissionDAO);
		//this.upermissionDAO = upermissionDAO;
	}
}
