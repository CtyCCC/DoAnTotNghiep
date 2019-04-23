package com.quiz.form;

import java.util.ArrayList;
import java.util.List;

public class QuesPos {
	private String idPos;
	private List<Object> listQues;
	public String getIdPos() {
		return idPos;
	}
	public void setIdPos(String idPos) {
		this.idPos = idPos;
	}
	public List<Object> getListQues() {
		return listQues;
	}
	public void setListQues(List<Object> listQues) {
		this.listQues = listQues;
	}
	public QuesPos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QuesPos(String idPos, List<Object> listQues) {
		super();
		this.idPos = idPos;
		this.listQues = listQues;
	}
	@Override
	public String toString() {
		return "QuesPos [idPos=" + idPos + ", listQues=" + listQues + "]";
	}
	
	
}
