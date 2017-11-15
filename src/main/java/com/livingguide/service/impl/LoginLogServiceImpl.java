package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.LoginLogDAO;
import com.livingguide.service.LoginLogService;
import com.livingguide.vo.LoginLogPO;
import com.livingguide.vo.query.LoginLogQueryModel;

@Service
public class LoginLogServiceImpl extends BaseService<LoginLogPO, LoginLogQueryModel> implements LoginLogService {

	//使用DAO时放开注释即可
	//private LoginLogDAO loginLogDAO;
	@Autowired
	public void setDAO(LoginLogDAO loginLogDAO){
		super.setDAO(loginLogDAO);
		//this.loginLogDAO = loginLogDAO;
	}
}
