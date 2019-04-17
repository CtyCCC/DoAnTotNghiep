package com.entity;

public class Description {
	private String id_pos;
	private double salary;
	private int quantity;
	private String edu;
	private int exp; // ?? year(s), if don't need: exp=0;
	private String location;
	private String info_pos;
	private String time_work;
	private String benefit;
	private String end_date;
	public String getId_pos() {
		return id_pos;
	}
	public void setId_pos(String id_pos) {
		this.id_pos = id_pos;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getInfo_pos() {
		return info_pos;
	}
	public void setInfo_pos(String info_pos) {
		this.info_pos = info_pos;
	}
	public String getTime_work() {
		return time_work;
	}
	public void setTime_work(String time_work) {
		this.time_work = time_work;
	}
	public String getBenefit() {
		return benefit;
	}
	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_pos == null) ? 0 : id_pos.hashCode());
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
		Description other = (Description) obj;
		if (id_pos == null) {
			if (other.id_pos != null)
				return false;
		} else if (!id_pos.equals(other.id_pos))
			return false;
		return true;
	}
	public Description(String id_pos, double salary, int quantity, String edu, int exp, String location,
			String info_pos, String time_work, String benefit, String end_date) {
		super();
		this.id_pos = id_pos;
		this.salary = salary;
		this.quantity = quantity;
		this.edu = edu;
		this.exp = exp;
		this.location = location;
		this.info_pos = info_pos;
		this.time_work = time_work;
		this.benefit = benefit;
		this.end_date = end_date;
	}
	public Description() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
