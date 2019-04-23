package com.entity;

import java.util.ArrayList;

public class Position {

	private String idPos;
	private String name;
	private String area;
	private String expDate;
	private String requirement;
	private String listQues;
	private String benefit;
	private String description;
	public String getIdPos() {
		return idPos;
	}
	public void setIdPos(String idPos) {
		this.idPos = idPos;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getListQues() {
		return listQues;
	}
	public void setListQues(String listQues) {
		this.listQues = listQues;
	}
	public String getBenefit() {
		return benefit;
	}
	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Position(String idPos, String name, String area, String expDate, String requirement, String listQues,
			String benefit, String description) {
		super();
		this.idPos = idPos;
		this.name = name;
		this.area = area;
		this.expDate = expDate;
		this.requirement = requirement;
		this.listQues = listQues;
		this.benefit = benefit;
		this.description = description;
	}
	public Position(String idPos, String name, String area, String expDate, String requirement,
			String benefit, String description) {
		super();
		this.idPos = idPos;
		this.name = name;
		this.area = area;
		this.expDate = expDate;
		this.requirement = requirement;
		this.benefit = benefit;
		this.description = description;
	}
	public Position() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
