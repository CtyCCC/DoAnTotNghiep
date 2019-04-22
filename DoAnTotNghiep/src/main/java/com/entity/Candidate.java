package com.entity;

import java.util.Map;


public class Candidate {
	private String idCan;
	private String nameCan;
	private String cmnd;
	private String email;
	private String phone;
	private boolean gender; // T: male, F: female
	private String dob;
	private String linkCV;
	private String namePos;
	private String dateImport;
	private Map<String,Object> rate;
	private String status;
	private Map<String,Object> interview;
	private	Map<String,Object> offer;
	private Map<String,Object> probation;
	
	public String getIdCan() {
		return idCan;
	}
	public void setIdCan(String idCan) {
		this.idCan = idCan;
	}
	public String getNameCan() {
		return nameCan;
	}
	public void setNameCan(String nameCan) {
		this.nameCan = nameCan;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getLinkCV() {
		return linkCV;
	}
	public void setLinkCV(String linkCV) {
		this.linkCV = linkCV;
	}
	public String getNamePos() {
		return namePos;
	}
	public void setNamePos(String namePos) {
		this.namePos = namePos;
	}
	public String getDateImport() {
		return dateImport;
	}
	public void setDateImport(String dateImport) {
		this.dateImport = dateImport;
	}
	public Map<String, Object> getRate() {
		return rate;
	}
	public void setRate(Map<String, Object> rate) {
		this.rate = rate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, Object> getInterview() {
		return interview;
	}
	public void setInterview(Map<String, Object> interview) {
		this.interview = interview;
	}
	public Map<String, Object> getOffer() {
		return offer;
	}
	public void setOffer(Map<String, Object> offer) {
		this.offer = offer;
	}
	public Map<String, Object> getProbation() {
		return probation;
	}
	public void setProbation(Map<String, Object> probation) {
		this.probation = probation;
	}
	@Override
	public String toString() {
		return "Candidate [idCan=" + idCan + ", nameCan=" + nameCan + ", cmnd=" + cmnd + ", email=" + email + ", phone="
				+ phone + ", gender=" + gender + ", dob=" + dob + ", linkCV=" + linkCV + ", namePos=" + namePos
				+ ", dateImport=" + dateImport + ", rate=" + rate + ", status=" + status + ", interview=" + interview
				+ ", offer=" + offer + ", probation=" + probation + "]";
	}
	public Candidate(String idCan, String nameCan, String cmnd, String email, String phone, boolean gender, String dob,
			String linkCV, String namePos, String dateImport, Map<String, Object> rate, String status,
			Map<String, Object> interview, Map<String, Object> offer, Map<String, Object> probation) {
		super();
		this.idCan = idCan;
		this.nameCan = nameCan;
		this.cmnd = cmnd;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.dob = dob;
		this.linkCV = linkCV;
		this.namePos = namePos;
		this.dateImport = dateImport;
		this.rate = rate;
		this.status = status;
		this.interview = interview;
		this.offer = offer;
		this.probation = probation;
	}
	public Candidate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Candidate(String idCan, String nameCan, String cmnd, String email, String phone, boolean gender, String dob,
			String linkCV, String namePos, String dateImport, String status) {
		super();
		this.idCan = idCan;
		this.nameCan = nameCan;
		this.cmnd = cmnd;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.dob = dob;
		this.linkCV = linkCV;
		this.namePos = namePos;
		this.dateImport = dateImport;
		this.status = status;
	}
	public Candidate(String idCan, String nameCan, String cmnd, String email, String phone, boolean gender, String dob,
			String linkCV, String namePos, String dateImport, Map<String, Object> rate, String status) {
		super();
		this.idCan = idCan;
		this.nameCan = nameCan;
		this.cmnd = cmnd;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.dob = dob;
		this.linkCV = linkCV;
		this.namePos = namePos;
		this.dateImport = dateImport;
		this.rate = rate;
		this.status = status;
	}
	
	
	
}