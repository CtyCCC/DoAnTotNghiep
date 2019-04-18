package com.candidate.entity;


public class Round {
	private String id_interview;
	private int id_round;
	private String date;
	private String time;
	private String id_interviewer;
	private String venue;
	private String note;
	private boolean result;
	public String getId_interview() {
		return id_interview;
	}
	public void setId_interview(String id_interview) {
		this.id_interview = id_interview;
	}
	public int getId_round() {
		return id_round;
	}
	public void setId_round(int id_round) {
		this.id_round = id_round;
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
	public String getId_interviewer() {
		return id_interviewer;
	}
	public void setId_interviewer(String id_interviewer) {
		this.id_interviewer = id_interviewer;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
		result = prime * result + ((id_interview == null) ? 0 : id_interview.hashCode());
		result = prime * result + id_round;
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
		Round other = (Round) obj;
		if (id_interview == null) {
			if (other.id_interview != null)
				return false;
		} else if (!id_interview.equals(other.id_interview))
			return false;
		if (id_round != other.id_round)
			return false;
		return true;
	}
	public Round(String id_interview, int id_round, String date, String time, String id_interviewer, String venue,
			String note, boolean result) {
		super();
		this.id_interview = id_interview;
		this.id_round = id_round;
		this.date = date;
		this.time = time;
		this.id_interviewer = id_interviewer;
		this.venue = venue;
		this.note = note;
		this.result = result;
	}
	public Round() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
