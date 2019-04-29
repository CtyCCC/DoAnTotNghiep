package com.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.login.form.FormLogin;

@Controller
public class LoginController {
	@GetMapping("/Login")
	public String showLogin(Model model) {
		FormLogin formlogin = new FormLogin();
		model.addAttribute("formlogin",formlogin);
		return "login";
	}
	
	@PostMapping("/Login")
	public String Login(@ModelAttribute FormLogin formlogin,HttpSession session) {
		System.out.println(session.getId());
		return "redirect:/index";
	}
}
