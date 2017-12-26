package com.livingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/exception")
public class ExceptionController {

	@RequestMapping(value = "validatecode")
	public Object validatecode(){
		return "exception/validatecode";
	}
}
