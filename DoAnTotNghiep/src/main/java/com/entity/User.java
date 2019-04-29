package com.entity;

public class User {
	private String id_user; //ID
	private String name;
	private String pass; //Password
	private String code; // 1:Manager(Admin); 2: interviewer
	private String useraccount;
	public String getId_user() {
		return id_user;
	}
	public void setId_user(String id_user) {
		this.id_user = id_user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	public User(String id_user, String name, String pass, String code, String useraccount) {
		super();
		this.id_user = id_user;
		this.name = name;
		this.pass = pass;
		this.code = code;
		this.useraccount = useraccount;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}