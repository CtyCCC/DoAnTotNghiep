package com.quiz.form;

public class ResultTest {
	private String idCan;
	private String score;
	private String time;
	private String total;
	public String getIdCan() {
		return idCan;
	}
	public void setIdCan(String idCan) {
		this.idCan = idCan;
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
	public ResultTest(String idCan, String score, String time, String total) {
		super();
		this.idCan = idCan;
		this.score = score;
		this.time = time;
		this.total = total;
	}
	public ResultTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
