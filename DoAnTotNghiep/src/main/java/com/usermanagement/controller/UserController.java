package com.usermanagement.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.User;
import com.usermanagement.dao.UserDAO;

@Controller
public class UserController {
	
	UserDAO dao = new UserDAO();
	
	@GetMapping("/userMgmt")
	public String userMgmt(Model model) {
		ArrayList<User> arrU = new ArrayList<>();
		arrU = dao.getAllUser();
		model.addAttribute("listUser",arrU);
		return "userManagement";
	}
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	public  @ResponseBody void  deleteUser( HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String tk = request.getParameter("tk");
		dao.deleteUser(id, tk);
		System.out.println("delete success");
	}
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public  @ResponseBody void  addNewUser( HttpServletRequest request) {
		
		//lấy mảng Pos để tạo id mới
		ArrayList<User> arrPos = new ArrayList<>();
		arrPos = dao.getAllUser();
		String idUser = dao.autoId(arrPos);
		
		String name = request.getParameter("name");
		String code = request.getParameter("role");
		String pass = request.getParameter("pass");
		String userName = request.getParameter("tk");
		String avatar = "https://docs.google.com/uc?id=1zTOyl_cNXdD5MIADpl75Dmr3tKrBy1Gd";
		User u = new User(idUser, userName, code, name, pass, avatar);
		dao.addNewUser(u);
		System.out.println("Add success user: "+u.getUserName());
	}
	
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public  @ResponseBody void update( HttpServletRequest request) {
		
		String name = request.getParameter("name");
		String code = request.getParameter("role");
		String id = request.getParameter("id");
		String tk = request.getParameter("tk");
		String avatar = "https://docs.google.com/uc?id=1zTOyl_cNXdD5MIADpl75Dmr3tKrBy1Gd";
		
		dao.updateUser(id, tk, name, code, avatar);
		System.out.println("update success");
	}

}
