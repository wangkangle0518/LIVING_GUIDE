package com.livingguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.livingguide.common.BComponent;
import com.livingguide.common.utils.pageutil.Page;
import com.livingguide.service.RoleService;
import com.livingguide.vo.RolePO;
import com.livingguide.vo.query.RoleQueryModel;

@Controller
@RequestMapping(value="/Urole")
public class RoleController extends BComponent {
	@Autowired
	private RoleService uroleService;
		
	@RequestMapping(value="add")
	@ResponseBody
	public Object add(RolePO m){
		return uroleService.create(m);
	}
	
	@RequestMapping(value="update")
	@ResponseBody
	public Object update(RolePO m){
		return uroleService.update(m);
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(@RequestParam("id") int id){
		return uroleService.delete(id);
	}
	
	@RequestMapping(value="queryByCriteria")
	@ResponseBody
	public Object queryByCriteria(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize, RoleQueryModel qm){
		qm.getPage().setPageNum(pageNo);
		qm.getPage().setPageSize(pageSize);		
		Page dbpage = uroleService.queryByPage(qm);
		return dbpage;
	}
	
}