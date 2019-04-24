package com.kam.tomcat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	@RequestMapping("/index")
	@ResponseBody
	public String index(String name) {
		return name;
	}
}
