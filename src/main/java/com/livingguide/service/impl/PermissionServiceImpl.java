package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.PermissionDAO;
import com.livingguide.service.PermissionService;
import com.livingguide.vo.PermissionPO;
import com.livingguide.vo.query.PermissionQueryModel;

@Service
public class PermissionServiceImpl extends BaseService<PermissionPO, PermissionQueryModel> implements PermissionService {

	//使用DAO时放开注释即可
	//private UpermissionDAO upermissionDAO;
	@Autowired
	public void setDAO(PermissionDAO upermissionDAO){
		super.setDAO(upermissionDAO);
		//this.upermissionDAO = upermissionDAO;
	}
}
