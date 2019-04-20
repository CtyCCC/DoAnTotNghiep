package com.quiz.form;

import java.util.List;

public class QuesPos {
	private String idPos;
	private List<String> listQues;
	public String getIdPos() {
		return idPos;
	}
	public void setIdPos(String idPos) {
		this.idPos = idPos;
	}
	public List<String> getListQues() {
		return listQues;
	}
	public void setListQues(List<String> listQues) {
		this.listQues = listQues;
	}
	public QuesPos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QuesPos(String idPos, List<String> listQues) {
		super();
		this.idPos = idPos;
		this.listQues = listQues;
	}
	
	
}
