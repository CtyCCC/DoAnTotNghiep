package com.usermanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/userMgmt")
	public String userMgmt() {
		return "userManagement";
	}

}
