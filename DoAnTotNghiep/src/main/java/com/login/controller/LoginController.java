package com.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.login.dao.LoginDAO;

@Controller
public class LoginController {
	
	LoginDAO logindao =new LoginDAO();
	
	@GetMapping("/Login")
	public String showLogin(Model model) {
		return "login";
	}
	
	@GetMapping("/401")
	public String showerror401() {
		return "401";
	}
	
	
	@PostMapping("/Login_user")
	public String Login() {
		return "index";
	}
}
