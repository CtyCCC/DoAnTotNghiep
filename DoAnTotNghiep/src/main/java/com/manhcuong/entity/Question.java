package com.manhcuong.entity;

import java.util.Arrays;

public class Question {
	private String id_ques;
	private String ask;
	private String[] answer;
	private int result;
	public String getId_ques() {
		return id_ques;
	}
	public void setId_ques(String id_ques) {
		this.id_ques = id_ques;
	}
	public String getAsk() {
		return ask;
	}
	public void setAsk(String ask) {
		this.ask = ask;
	}
	public String[] getAnswer() {
		return answer;
	}
	public void setAnswer(String[] answer) {
		this.answer = answer;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_ques == null) ? 0 : id_ques.hashCode());
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
		Question other = (Question) obj;
		if (id_ques == null) {
			if (other.id_ques != null)
				return false;
		} else if (!id_ques.equals(other.id_ques))
			return false;
		return true;
	}
	public Question(String id_ques, String ask, String[] answer, int result) {
		super();
		this.id_ques = id_ques;
		this.ask = ask;
		this.answer = answer;
		this.result = result;
	}
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Question [id_ques=" + id_ques + ", ask=" + ask + ", answer=" + Arrays.toString(answer) + ", result="
				+ result + "]";
	}
	
}
