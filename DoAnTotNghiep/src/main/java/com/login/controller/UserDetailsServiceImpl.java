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
		com.entity.User userlogin = logindao.getInformationUser(username);
		System.out.println(userlogin.toString());
		if(userlogin == null) {
			System.out.println("User not found! " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
		grantList.add(new SimpleGrantedAuthority(userlogin.getCode()));
		BCryptPasswordEncoder encode  = new BCryptPasswordEncoder();
		String encodePass = encode.encode(userlogin.getPass());
		
		UserDetails userDetail =(UserDetails)new User(userlogin.getTk(),encodePass,grantList);
		return userDetail;
	}
}
