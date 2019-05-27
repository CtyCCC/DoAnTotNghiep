package com.candidate.form;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.entity.Candidate;

public class FormEditProfile{
	
	private String idCan;
	private String name;
	private String cmnd;
	private String email;
	private Boolean gender;
	private String namePos;
	private String DOB;
	private String phone;
	private String score;
	private String time;
	private String total;
	private String dateImport;
	private String workExp;
	private String linkCV;
	private String linkAvatar;
	private MultipartFile cv;
	private MultipartFile avatar;
	public String getIdCan() {
		return idCan;
	}
	public void setIdCan(String idCan) {
		this.idCan = idCan;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCmnd() {
		return cmnd;
	}
	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public String getNamePos() {
		return namePos;
	}
	public void setNamePos(String namePos) {
		this.namePos = namePos;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getDateImport() {
		return dateImport;
	}
	public void setDateImport(String dateImport) {
		this.dateImport = dateImport;
	}
	public String getWorkExp() {
		return workExp;
	}
	public void setWorkExp(String workExp) {
		this.workExp = workExp;
	}
	public String getLinkCV() {
		return linkCV;
	}
	public void setLinkCV(String linkCV) {
		this.linkCV = linkCV;
	}
	public String getLinkAvatar() {
		return linkAvatar;
	}
	public void setLinkAvatar(String linkAvatar) {
		this.linkAvatar = linkAvatar;
	}
	public MultipartFile getCv() {
		return cv;
	}
	public void setCv(MultipartFile cv) {
		this.cv = cv;
	}
	public MultipartFile getAvatar() {
		return avatar;
	}
	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}
	public FormEditProfile(String idCan, String name, String cmnd, String email, Boolean gender, String namePos,
			String dOB, String phone, String score, String time, String total, String dateImport, String workExp,
			String linkCV, String linkAvatar, MultipartFile cv, MultipartFile avatar) {
		super();
		this.idCan = idCan;
		this.name = name;
		this.cmnd = cmnd;
		this.email = email;
		this.gender = gender;
		this.namePos = namePos;
		DOB = dOB;
		this.phone = phone;
		this.score = score;
		this.time = time;
		this.total = total;
		this.dateImport = dateImport;
		this.workExp = workExp;
		this.linkCV = linkCV;
		this.linkAvatar = linkAvatar;
		this.cv = null;
		this.avatar = null;
	}
	public FormEditProfile() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
