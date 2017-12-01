package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.UserRoleDAO;
import com.livingguide.service.UserRoleService;
import com.livingguide.vo.UserRolePO;
import com.livingguide.vo.query.UserRoleQueryModel;

@Service
public class UserRoleServiceImpl extends BaseService<UserRolePO, UserRoleQueryModel> implements UserRoleService {

	//使用DAO时放开注释即可
	//private UuserRoleDAO uuserRoleDAO;
	@Autowired
	public void setDAO(UserRoleDAO uuserRoleDAO){
		super.setDAO(uuserRoleDAO);
		//this.uuserRoleDAO = uuserRoleDAO;
	}
}
