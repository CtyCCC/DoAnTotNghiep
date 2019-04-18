package com.quiz.form;

public class Result {
	
	private String idQues;
	private String answer;
	public String getIdQues() {
		return idQues;
	}
	public void setIdQues(String idQues) {
		this.idQues = idQues;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Result(String idQues, String answer) {
		super();
		this.idQues = idQues;
		this.answer = answer;
	}
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
