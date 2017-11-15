package com.livingguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.livingguide.common.BComponent;
import com.livingguide.common.utils.pageutil.Page;
import com.livingguide.service.RegisterService;
import com.livingguide.vo.RegisterPO;
import com.livingguide.vo.query.RegisterQueryModel;

@Controller
@RequestMapping(value="/Register")
public class RegisterController extends BComponent {
	@Autowired
	private RegisterService registerService;
		
	@RequestMapping(value="add")
	@ResponseBody
	public Object add(RegisterPO m){
		return registerService.create(m);
	}
	
	@RequestMapping(value="update")
	@ResponseBody
	public Object update(RegisterPO m){
		return registerService.update(m);
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(@RequestParam("id") int id){
		return registerService.delete(id);
	}
	
	@RequestMapping(value="queryByCriteria")
	@ResponseBody
	public Object queryByCriteria(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize, RegisterQueryModel qm){
		qm.getPage().setPageNum(pageNo);
		qm.getPage().setPageSize(pageSize);		
		Page dbpage = registerService.queryByPage(qm);
		return dbpage;
	}
	
}