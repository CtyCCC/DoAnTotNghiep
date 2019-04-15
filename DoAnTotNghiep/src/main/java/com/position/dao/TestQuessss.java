package com.position.dao;

import java.util.ArrayList;

import com.position.entity.Questions;

public class TestQuessss {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		QuestionsDAO dao = new QuestionsDAO();
		ArrayList<Questions> arr =dao.getAllQuestions();
		int dem = 0;
		for (Questions questions : arr) {
			System.out.println(dem++);
			System.out.println(questions.getAnswer());
		}

	}

}
