package com.livingguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.livingguide.common.BComponent;
import com.livingguide.common.utils.pageutil.Page;
import com.livingguide.service.UuserRoleService;
import com.livingguide.vo.UuserRolePO;
import com.livingguide.vo.query.UuserRoleQueryModel;

@Controller
@RequestMapping(value="/UuserRole")
public class UuserRoleController extends BComponent {
	@Autowired
	private UuserRoleService uuserRoleService;
		
	@RequestMapping(value="add")
	@ResponseBody
	public Object add(UuserRolePO m){
		return uuserRoleService.create(m);
	}
	
	@RequestMapping(value="update")
	@ResponseBody
	public Object update(UuserRolePO m){
		return uuserRoleService.update(m);
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(@RequestParam("id") int id){
		return uuserRoleService.delete(id);
	}
	
	@RequestMapping(value="queryByCriteria")
	@ResponseBody
	public Object queryByCriteria(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize, UuserRoleQueryModel qm){
		qm.getPage().setPageNum(pageNo);
		qm.getPage().setPageSize(pageSize);		
		Page dbpage = uuserRoleService.queryByPage(qm);
		return dbpage;
	}
	
}