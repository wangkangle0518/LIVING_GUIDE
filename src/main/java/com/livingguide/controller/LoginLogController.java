package com.livingguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.livingguide.common.BComponent;
import com.livingguide.common.utils.pageutil.Page;
import com.livingguide.service.LoginLogService;
import com.livingguide.vo.LoginLogPO;
import com.livingguide.vo.query.LoginLogQueryModel;

@Controller
@RequestMapping(value="/LoginLog")
public class LoginLogController extends BComponent {
	@Autowired
	private LoginLogService loginLogService;
		
	@RequestMapping(value="add")
	@ResponseBody
	public Object add(LoginLogPO m){
		return loginLogService.create(m);
	}
	
	@RequestMapping(value="update")
	@ResponseBody
	public Object update(LoginLogPO m){
		return loginLogService.update(m);
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(@RequestParam("id") int id){
		return loginLogService.delete(id);
	}
	
	@RequestMapping(value="queryByCriteria")
	@ResponseBody
	public Object queryByCriteria(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize, LoginLogQueryModel qm){
		qm.getPage().setPageNum(pageNo);
		qm.getPage().setPageSize(pageSize);		
		Page dbpage = loginLogService.queryByPage(qm);
		return dbpage;
	}
	
}