package com.entity;

public class User {
	private String id_user; //ID
	private String tk;
	private String code; // 1:Manager(Admin); 2: interviewer
	private String name;
	private String pass; //Password
	public String getId_user() {
		return id_user;
	}
	public void setId_user(String id_user) {
		this.id_user = id_user;
	}
	public String getTk() {
		return tk;
	}
	public void setTk(String tk) {
		this.tk = tk;
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
	public User(String id_user, String tk, String code, String name, String pass) {
		super();
		this.id_user = id_user;
		this.tk = tk;
		this.code = code;
		this.name = name;
		this.pass = pass;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id_user=" + id_user + ", tk=" + tk + ", code=" + code + ", name=" + name + ", pass=" + pass + "]";
	}

	
}