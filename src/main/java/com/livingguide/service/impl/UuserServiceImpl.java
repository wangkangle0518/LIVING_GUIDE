package com.livingguide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livingguide.common.service.BaseService;
import com.livingguide.dao.UuserDAO;
import com.livingguide.service.UuserService;
import com.livingguide.vo.UuserPO;
import com.livingguide.vo.query.UuserQueryModel;

@Service("uuserService")
public class UuserServiceImpl extends BaseService<UuserPO, UuserQueryModel> implements UuserService {

	//使用DAO时放开注释即可
	//private UuserDAO uuserDAO;
	@Autowired
	public void setDAO(UuserDAO uuserDAO){
		super.setDAO(uuserDAO);
		//this.uuserDAO = uuserDAO;
	}
}
