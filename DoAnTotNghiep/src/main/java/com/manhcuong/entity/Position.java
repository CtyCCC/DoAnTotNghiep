package com.manhcuong.entity;

import java.util.ArrayList;

public class Position {
	private String id_pos;
	private String name_pos;
	private String requirment;
	private ArrayList<Question> lsQues;
	public String getId_pos() {
		return id_pos;
	}
	public void setId_pos(String id_pos) {
		this.id_pos = id_pos;
	}
	public String getName_pos() {
		return name_pos;
	}
	public void setName_pos(String name_pos) {
		this.name_pos = name_pos;
	}
	public String getRequirment() {
		return requirment;
	}
	public void setRequirment(String requirment) {
		this.requirment = requirment;
	}
	public ArrayList<Question> getLsQues() {
		return lsQues;
	}
	public void setLsQues(ArrayList<Question> lsQues) {
		this.lsQues = lsQues;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_pos == null) ? 0 : id_pos.hashCode());
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
		Position other = (Position) obj;
		if (id_pos == null) {
			if (other.id_pos != null)
				return false;
		} else if (!id_pos.equals(other.id_pos))
			return false;
		return true;
	}
	public Position(String id_pos, String name_pos, String requirment) {
		super();
		this.id_pos = id_pos;
		this.name_pos = name_pos;
		this.requirment = requirment;
	}
	public Position() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
