package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.RegisterDAO;
import com.livingguide.service.RegisterService;
import com.livingguide.vo.RegisterPO;
import com.livingguide.vo.query.RegisterQueryModel;

@Service
public class RegisterServiceImpl extends BaseService<RegisterPO, RegisterQueryModel> implements RegisterService {

	//使用DAO时放开注释即可
	//private RegisterDAO registerDAO;
	@Autowired
	public void setDAO(RegisterDAO registerDAO){
		super.setDAO(registerDAO);
		//this.registerDAO = registerDAO;
	}
}
