package com.livingguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.livingguide.common.BComponent;
import com.livingguide.common.utils.pageutil.Page;
import com.livingguide.service.UuserService;
import com.livingguide.vo.UuserPO;
import com.livingguide.vo.query.UuserQueryModel;

@Controller
@RequestMapping(value="/Uuser")
public class UuserController extends BComponent {
	@Autowired
	private UuserService uuserService;
		
	@RequestMapping(value="add")
	@ResponseBody
	public Object add(UuserPO m){
		return uuserService.create(m);
	}
	
	@RequestMapping(value="update")
	@ResponseBody
	public Object update(UuserPO m){
		return uuserService.update(m);
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(@RequestParam("id") int id){
		return uuserService.delete(id);
	}
	
	@RequestMapping(value="queryByCriteria")
	@ResponseBody
	public Object queryByCriteria(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize, UuserQueryModel qm){
		qm.getPage().setPageNum(pageNo);
		qm.getPage().setPageSize(pageSize);		
		Page dbpage = uuserService.queryByPage(qm);
		return dbpage;
	}
	
}