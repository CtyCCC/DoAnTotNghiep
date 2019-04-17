package com.entity;

public class User {
	private String id_user; //ID
	private String name;
	private String pass; //Password
	private int code; // 1:Manager(Admin); 2: interviewer
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
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_user == null) ? 0 : id_user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id_user == null) {
			if (other.id_user != null)
				return false;
		} else if (!id_user.equals(other.id_user))
			return false;
		return true;
	}
	public User(String id_user, String name, String pass, int code) {
		super();
		this.id_user = id_user;
		this.name = name;
		this.pass = pass;
		this.code = code;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
