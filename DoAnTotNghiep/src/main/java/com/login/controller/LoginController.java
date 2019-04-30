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
	
	@GetMapping("/Error")
	public String showerror() {
		return "error";
	}
	
	@PostMapping("/Login_user")
	public String Login() {
		return "index";
	}
}
