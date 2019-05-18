package com.entity;

public class User {
	private String idUser; //ID
	private String userName;
	private String code; // 1:Manager(Admin); 2: interviewer
	private String name;
	private String pass; //Password
	private String avatar;
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public User(String idUser, String userName, String code, String name, String pass, String avatar) {
		super();
		this.idUser = idUser;
		this.userName = userName;
		this.code = code;
		this.name = name;
		this.pass = pass;
		this.avatar = avatar;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", userName=" + userName + ", code=" + code + ", name=" + name + ", pass="
				+ pass + ", avatar=" + avatar + "]";
	}
	
	

	
}