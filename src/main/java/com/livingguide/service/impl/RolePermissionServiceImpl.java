package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.RolePermissionDAO;
import com.livingguide.service.RolePermissionService;
import com.livingguide.vo.RolePermissionPO;
import com.livingguide.vo.query.RolePermissionQueryModel;

@Service
public class RolePermissionServiceImpl extends BaseService<RolePermissionPO, RolePermissionQueryModel> implements RolePermissionService {

	//使用DAO时放开注释即可
	//private UrolePermissionDAO urolePermissionDAO;
	@Autowired
	public void setDAO(RolePermissionDAO urolePermissionDAO){
		super.setDAO(urolePermissionDAO);
		//this.urolePermissionDAO = urolePermissionDAO;
	}
}
