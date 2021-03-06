package com.livingguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.livingguide.common.BComponent;
import com.livingguide.common.utils.pageutil.Page;
import com.livingguide.service.RolePermissionService;
import com.livingguide.vo.RolePermissionPO;
import com.livingguide.vo.query.RolePermissionQueryModel;

@Controller
@RequestMapping(value="/UrolePermission")
public class RolePermissionController extends BComponent {
	@Autowired
	private RolePermissionService urolePermissionService;
		
	@RequestMapping(value="add")
	@ResponseBody
	public Object add(RolePermissionPO m){
		return urolePermissionService.create(m);
	}
	
	@RequestMapping(value="update")
	@ResponseBody
	public Object update(RolePermissionPO m){
		return urolePermissionService.update(m);
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(@RequestParam("id") int id){
		return urolePermissionService.delete(id);
	}
	
	@RequestMapping(value="queryByCriteria")
	@ResponseBody
	public Object queryByCriteria(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize, RolePermissionQueryModel qm){
		qm.getPage().setPageNum(pageNo);
		qm.getPage().setPageSize(pageSize);		
		Page dbpage = urolePermissionService.queryByPage(qm);
		return dbpage;
	}
	
}