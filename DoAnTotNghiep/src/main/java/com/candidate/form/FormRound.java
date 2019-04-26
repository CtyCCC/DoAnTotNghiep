package com.candidate.form;

public class FormRound {
	private String id;
	private String round;
	private String interviewer;
	private String date;
	private String time;
	private String note;
	private String venue;
	private String result;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public String getInterviewer() {
		return interviewer;
	}
	public void setInterviewer(String interviewer) {
		this.interviewer = interviewer;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public FormRound(String id, String round, String interviewer, String date, String time, String note, String venue,
			String result) {
		super();
		this.id = id;
		this.round = round;
		this.interviewer = interviewer;
		this.date = date;
		this.time = time;
		this.note = note;
		this.venue = venue;
		this.result = result;
	}
	public FormRound() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FormRound [id=" + id + ", round=" + round + ", interviewer=" + interviewer + ", date=" + date
				+ ", time=" + time + ", note=" + note + ", venue=" + venue + ", result=" + result + "]";
	}
	
	
}
