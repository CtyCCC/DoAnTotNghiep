package com.candidate.entity;

public class Offer {
	private String id_offer; // == id_can;
	private double cur_salary;
	private double ex_salary;
	private double of_salary;
	private boolean result;
	public String getId_offer() {
		return id_offer;
	}
	public void setId_offer(String id_offer) {
		this.id_offer = id_offer;
	}
	public double getCur_salary() {
		return cur_salary;
	}
	public void setCur_salary(double cur_salary) {
		this.cur_salary = cur_salary;
	}
	public double getEx_salary() {
		return ex_salary;
	}
	public void setEx_salary(double ex_salary) {
		this.ex_salary = ex_salary;
	}
	public double getOf_salary() {
		return of_salary;
	}
	public void setOf_salary(double of_salary) {
		this.of_salary = of_salary;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_offer == null) ? 0 : id_offer.hashCode());
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
		Offer other = (Offer) obj;
		if (id_offer == null) {
			if (other.id_offer != null)
				return false;
		} else if (!id_offer.equals(other.id_offer))
			return false;
		return true;
	}
	public Offer(String id_offer, double cur_salary, double ex_salary, double of_salary, boolean result) {
		super();
		this.id_offer = id_offer;
		this.cur_salary = cur_salary;
		this.ex_salary = ex_salary;
		this.of_salary = of_salary;
		this.result = result;
	}
	public Offer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
