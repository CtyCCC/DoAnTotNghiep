package com.apply.form;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;




public class FormApply {
	@NotEmpty(message = "Khonmg duoc rong")
	private String f_idcard;
	@NotEmpty(message = "Khonmg duoc rong")
	private String f_name;
	private String f_gender;
	private String f_DOB;
	@NotEmpty(message = "Khonmg duoc rong")
	private String f_position;
	@NotEmpty(message = "Khonmg duoc rong")
	@Email(message = "Sai dinh dang email")
	private String f_email;
	@NotEmpty(message = "Khonmg duoc rong")
	private String f_phone;
	private String f_skill;
	private String f_workExp;
	private MultipartFile f_filedata;
	public String getF_idcard() {
		return f_idcard;
	}
	public void setF_idcard(String f_idcard) {
		this.f_idcard = f_idcard;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
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
	public String getF_workExp() {
		return f_workExp;
	}
	public void setF_workExp(String f_workExp) {
		this.f_workExp = f_workExp;
	}
	public MultipartFile getF_filedata() {
		return f_filedata;
	}
	public void setF_filedata(MultipartFile f_filedata) {
		this.f_filedata = f_filedata;
	}
	public FormApply(String f_idcard, String f_name, String f_gender, String f_DOB, String f_position,
			String f_email, String f_phone, String f_skill, String f_workExp, MultipartFile f_filedata) {
		super();
		this.f_idcard = f_idcard;
		this.f_name = f_name;
		this.f_gender = f_gender;
		this.f_DOB = f_DOB;
		this.f_position = f_position;
		this.f_email = f_email;
		this.f_phone = f_phone;
		this.f_skill = f_skill;
		this.f_workExp = f_workExp;
		this.f_filedata = f_filedata;
	}
	public FormApply() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FormApply [f_idcard=" + f_idcard + ", f_name=" + f_name + ", f_gender=" + f_gender + ", f_DOB=" + f_DOB
				+ ", f_position=" + f_position + ", f_email=" + f_email + ", f_phone=" + f_phone + ", f_skill="
				+ f_skill + ", f_workExp=" + f_workExp + ", f_filedata=" + f_filedata + "]";
	}
	
	
	
	
}
