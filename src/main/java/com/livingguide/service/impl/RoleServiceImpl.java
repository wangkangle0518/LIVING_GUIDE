package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.RoleDAO;
import com.livingguide.service.RoleService;
import com.livingguide.vo.RolePO;
import com.livingguide.vo.query.RoleQueryModel;

@Service
public class RoleServiceImpl extends BaseService<RolePO, RoleQueryModel> implements RoleService {

	//使用DAO时放开注释即可
	//private UroleDAO uroleDAO;
	@Autowired
	public void setDAO(RoleDAO uroleDAO){
		super.setDAO(uroleDAO);
		//this.uroleDAO = uroleDAO;
	}
}
