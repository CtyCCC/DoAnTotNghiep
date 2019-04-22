package com.apply.form;

import org.springframework.web.multipart.MultipartFile;

public class FormApply {
	private String f_idcard;
	private String f_firstname;
	private String f_lastname;
	private String f_gender;
	private String f_DOB;
	private String f_position;
	private String f_email;
	private String f_phone;
	private String f_skill;
	private MultipartFile f_filedata;
	public String getF_idcard() {
		return f_idcard;
	}
	public void setF_idcard(String f_idcard) {
		this.f_idcard = f_idcard;
	}
	public String getF_firstname() {
		return f_firstname;
	}
	public void setF_firstname(String f_firstname) {
		this.f_firstname = f_firstname;
	}
	public String getF_lastname() {
		return f_lastname;
	}
	public void setF_lastname(String f_lastname) {
		this.f_lastname = f_lastname;
	}
	public String getF_gender() {
		return f_gender;
	}
	public void setF_gender(String f_gender) {
		this.f_gender = f_gender;
	}
	public String getF_DOB() {
		return f_DOB;
	}
	public void setF_DOB(String f_DOB) {
		this.f_DOB = f_DOB;
	}
	public String getF_position() {
		return f_position;
	}
	public void setF_position(String f_position) {
		this.f_position = f_position;
	}
	public String getF_email() {
		return f_email;
	}
	public void setF_email(String f_email) {
		this.f_email = f_email;
	}
	public String getF_phone() {
		return f_phone;
	}
	public void setF_phone(String f_phone) {
		this.f_phone = f_phone;
	}
	public String getF_skill() {
		return f_skill;
	}
	public void setF_skill(String f_skill) {
		this.f_skill = f_skill;
	}
	public MultipartFile getF_filedata() {
		return f_filedata;
	}
	public void setF_filedata(MultipartFile f_filedata) {
		this.f_filedata = f_filedata;
	}
	public FormApply(String f_idcard, String f_firstname, String f_lastname, String f_gender, String f_DOB,
			String f_position, String f_email, String f_phone, String f_skill, MultipartFile f_filedata) {
		super();
		this.f_idcard = f_idcard;
		this.f_firstname = f_firstname;
		this.f_lastname = f_lastname;
		this.f_gender = f_gender;
		this.f_DOB = f_DOB;
		this.f_position = f_position;
		this.f_email = f_email;
		this.f_phone = f_phone;
		this.f_skill = f_skill;
		this.f_filedata = f_filedata;
	}
	public FormApply() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
