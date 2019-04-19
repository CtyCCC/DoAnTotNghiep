package com.apply.form;

public class TitlePosition {
	private String idPos;
	private String name;
	private String area;
	private String expDate;
	
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

	public TitlePosition(String idPos, String name, String area, String expDate) {
		super();
		this.idPos = idPos;
		this.name = name;
		this.area = area;
		this.expDate = expDate;
	}
	public TitlePosition() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}