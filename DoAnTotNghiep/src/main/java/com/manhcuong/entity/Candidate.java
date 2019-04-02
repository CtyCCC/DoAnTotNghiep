package com.manhcuong.entity;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class Candidate {
	private String id_can;
	private String name_can;
	private String cmnd;
	private String email;
	private String phone;
	private String gender; // female ,  male
	private String dob;
	private String linkCV;
	private String id_pos;
	private Map<String, AttributeValue> rate;
	private String status;
	private Map<String, AttributeValue> interview;
	private	Map<String, AttributeValue> offer;
	private Map<String, AttributeValue> probation;
	
	public String getId_can() {
		return id_can;
	}

	public void setId_can(String id_can) {
		this.id_can = id_can;
	}

	public String getName_can() {
		return name_can;
	}

	public void setName_can(String name_can) {
		this.name_can = name_can;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
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

	public String getId_pos() {
		return id_pos;
	}

	public void setId_pos(String id_pos) {
		this.id_pos = id_pos;
	}

	public Map<String, AttributeValue> getRate() {
		return rate;
	}

	public void setRate(Map<String, AttributeValue> rate) {
		this.rate = rate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, AttributeValue> getInterview() {
		return interview;
	}

	public void setInterview(Map<String, AttributeValue> interview) {
		this.interview = interview;
	}

	public Map<String, AttributeValue> getOffer() {
		return offer;
	}

	public void setOffer(Map<String, AttributeValue> offer) {
		this.offer = offer;
	}

	public Map<String, AttributeValue> getProbation() {
		return probation;
	}

	public void setProbation(Map<String, AttributeValue> probation) {
		this.probation = probation;
	}
	
	
	public Candidate(String id_can, String name_can, String cmnd, String email, String phone, String gender, String dob,
			String linkCV, String id_pos, Map<String, AttributeValue> rate, String status,
			Map<String, AttributeValue> interview, Map<String, AttributeValue> offer,
			Map<String, AttributeValue> probation) {
		super();
		this.id_can = id_can;
		this.name_can = name_can;
		this.cmnd = cmnd;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.dob = dob;
		this.linkCV = linkCV;
		this.id_pos = id_pos;
		this.rate = rate;
		this.status = status;
		this.interview = interview;
		this.offer = offer;
		this.probation = probation;
	}

	@Override
	public String toString() {
		return "Candidate [id_can=" + id_can + ", name_can=" + name_can + ", cmnd=" + cmnd + ", email=" + email
				+ ", phone=" + phone + ", gender=" + gender + ", dob=" + dob + ", linkCV=" + linkCV + ", id_pos="
				+ id_pos + ", rate=" + rate + ", status=" + status + ", interview=" + interview + ", offer=" + offer
				+ ", probation=" + probation + "]\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmnd == null) ? 0 : cmnd.hashCode());
		result = prime * result + ((id_can == null) ? 0 : id_can.hashCode());
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
		Candidate other = (Candidate) obj;
		if (cmnd == null) {
			if (other.cmnd != null)
				return false;
		} else if (!cmnd.equals(other.cmnd))
			return false;
		if (id_can == null) {
			if (other.id_can != null)
				return false;
		} else if (!id_can.equals(other.id_can))
			return false;
		return true;
	}

	public Candidate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}