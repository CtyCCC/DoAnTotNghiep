package com.candidate.entity;


public class Interview {
	private String id_interview; //== id_can
	private boolean finalResult;
	public String getId_interview() {
		return id_interview;
	}
	public void setId_interview(String id_interview) {
		this.id_interview = id_interview;
	}
	public boolean isFinalResult() {
		return finalResult;
	}
	public void setFinalResult(boolean finalResult) {
		this.finalResult = finalResult;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_interview == null) ? 0 : id_interview.hashCode());
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
		Interview other = (Interview) obj;
		if (id_interview == null) {
			if (other.id_interview != null)
				return false;
		} else if (!id_interview.equals(other.id_interview))
			return false;
		return true;
	}
	public Interview(String id_interview, boolean finalResult) {
		super();
		this.id_interview = id_interview;
		this.finalResult = finalResult;
	}
	public Interview() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
