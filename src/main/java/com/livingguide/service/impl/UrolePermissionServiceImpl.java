package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.UrolePermissionDAO;
import com.livingguide.service.UrolePermissionService;
import com.livingguide.vo.UrolePermissionPO;
import com.livingguide.vo.query.UrolePermissionQueryModel;

@Service
public class UrolePermissionServiceImpl extends BaseService<UrolePermissionPO, UrolePermissionQueryModel> implements UrolePermissionService {

	//使用DAO时放开注释即可
	//private UrolePermissionDAO urolePermissionDAO;
	@Autowired
	public void setDAO(UrolePermissionDAO urolePermissionDAO){
		super.setDAO(urolePermissionDAO);
		//this.urolePermissionDAO = urolePermissionDAO;
	}
}
