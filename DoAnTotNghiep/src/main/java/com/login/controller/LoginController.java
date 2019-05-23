package com.login.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.login.dao.LoginDAO;

@Controller
public class LoginController {
	@Autowired
	LoginDAO logindao;
	
	@GetMapping("/default")
	public String navigation(HttpServletRequest req) {
		
		if(req.isUserInRole("ADMIN")) {
			return "redirect:/userMgmt";
		}
		else if(req.isUserInRole("HR") || req.isUserInRole("INTERVIEWER")) {
			return "redirect:/candidate";
		}
		return "/logout";
	}
	
	@GetMapping("/Loginfail")
	public String loginFail(Model model) {
		model.addAttribute("loginfail","UserName or Password incorrect");
		return "login";
	}
	
	@GetMapping("/Login")
	public String showLogin(Model model) {
		return "login";
	}
	
	@GetMapping("/403")
	public String showerror403() {
		return "403";
	}
	
}
