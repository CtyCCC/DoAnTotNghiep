package com.manhcuong.entity;

public class Probation {
	private String id_pro; // == id_can
	private String dateRange;
	private boolean result;
	private String note;
	public String getId_pro() {
		return id_pro;
	}
	public void setId_pro(String id_pro) {
		this.id_pro = id_pro;
	}
	public String getDateRange() {
		return dateRange;
	}
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_pro == null) ? 0 : id_pro.hashCode());
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
		Probation other = (Probation) obj;
		if (id_pro == null) {
			if (other.id_pro != null)
				return false;
		} else if (!id_pro.equals(other.id_pro))
			return false;
		return true;
	}
	public Probation(String id_pro, String dateRange, boolean result, String note) {
		super();
		this.id_pro = id_pro;
		this.dateRange = dateRange;
		this.result = result;
		this.note = note;
	}
	public Probation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
