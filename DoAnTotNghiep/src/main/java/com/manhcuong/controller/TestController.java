package com.manhcuong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/profile.html")
	public String profile() {
		return "profile";
	}
	
	@GetMapping("/position.html")
	public String position() {
		return "position";
	}
}
