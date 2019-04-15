package com.position.entity;


public class Questions {
	
	private String idQues;
	private String content;
	private String a;
	private String b;
	private String c;
	private String d;
	private String answer;
	public String getIdQues() {
		return idQues;
	}
	public void setIdQues(String idQues) {
		this.idQues = idQues;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Questions() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Questions(String idQues, String content, String a, String b, String c, String d, String answer) {
		super();
		this.idQues = idQues;
		this.content = content;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "Questions [idQues=" + idQues + ", content=" + content + ", a=" + a + ", b=" + b + ", c=" + c + ", d="
				+ d + ", answer=" + answer + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idQues == null) ? 0 : idQues.hashCode());
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
		Questions other = (Questions) obj;
		if (idQues == null) {
			if (other.idQues != null)
				return false;
		} else if (!idQues.equals(other.idQues))
			return false;
		return true;
	}
	
	

}
