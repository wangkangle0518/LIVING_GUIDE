package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.UuserRoleDAO;
import com.livingguide.service.UuserRoleService;
import com.livingguide.vo.UuserRolePO;
import com.livingguide.vo.query.UuserRoleQueryModel;

@Service
public class UuserRoleServiceImpl extends BaseService<UuserRolePO, UuserRoleQueryModel> implements UuserRoleService {

	//使用DAO时放开注释即可
	//private UuserRoleDAO uuserRoleDAO;
	@Autowired
	public void setDAO(UuserRoleDAO uuserRoleDAO){
		super.setDAO(uuserRoleDAO);
		//this.uuserRoleDAO = uuserRoleDAO;
	}
}
