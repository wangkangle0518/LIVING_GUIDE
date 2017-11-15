package com.livingguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.livingguide.common.BComponent;
import com.livingguide.common.utils.pageutil.Page;
import com.livingguide.service.UpermissionService;
import com.livingguide.vo.UpermissionPO;
import com.livingguide.vo.query.UpermissionQueryModel;

@Controller
@RequestMapping(value="/Upermission")
public class UpermissionController extends BComponent {
	@Autowired
	private UpermissionService upermissionService;
		
	@RequestMapping(value="add")
	@ResponseBody
	public Object add(UpermissionPO m){
		return upermissionService.create(m);
	}
	
	@RequestMapping(value="update")
	@ResponseBody
	public Object update(UpermissionPO m){
		return upermissionService.update(m);
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(@RequestParam("id") int id){
		return upermissionService.delete(id);
	}
	
	@RequestMapping(value="queryByCriteria")
	@ResponseBody
	public Object queryByCriteria(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize, UpermissionQueryModel qm){
		qm.getPage().setPageNum(pageNo);
		qm.getPage().setPageSize(pageSize);		
		Page dbpage = upermissionService.queryByPage(qm);
		return dbpage;
	}
	
}