package com.entity;

public class Position {

	private String idPos;
	private String name;
	private String area;
	private String expDate;
	private String requirement;
	private String benefit;
	private String description;
	public String getidPos() {
		return idPos;
	}
	public void setidPos(String idPos) {
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
	public Position() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Position(String idPos, String name, String area, String expDate, String requirement, String benefit,
			String description) {
		super();
		this.idPos = idPos;
		this.name = name;
		this.area = area;
		this.expDate = expDate;
		this.requirement = requirement;
		this.benefit = benefit;
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPos == null) ? 0 : idPos.hashCode());
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
		Position other = (Position) obj;
		if (idPos == null) {
			if (other.idPos != null)
				return false;
		} else if (!idPos.equals(other.idPos))
			return false;
		return true;
	}
	

}
