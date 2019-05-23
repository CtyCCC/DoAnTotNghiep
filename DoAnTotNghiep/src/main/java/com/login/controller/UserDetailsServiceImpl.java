package com.login.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.login.dao.LoginDAO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private LoginDAO logindao;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserDetails userDetail = (UserDetails)new User("not account","not account",new ArrayList<GrantedAuthority>());
		com.entity.User userlogin = logindao.getInformationUser(username);
		if(userlogin != null) {
			List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
			grantList.add(new SimpleGrantedAuthority(userlogin.getCode()));
			BCryptPasswordEncoder encode  = new BCryptPasswordEncoder();
			String encodePass = encode.encode(userlogin.getPass());
			userDetail =(UserDetails)new User(userlogin.getUserName(),encodePass,grantList);
		}
		return userDetail;
	}
	
}
