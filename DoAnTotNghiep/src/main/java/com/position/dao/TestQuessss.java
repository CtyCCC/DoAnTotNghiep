package com.position.dao;

import java.util.ArrayList;

import com.position.entity.Position;
import com.position.entity.Questions;

public class TestQuessss {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		QuestionsDAO dao = new QuestionsDAO();
//		ArrayList<Questions> arr =dao.getAllQuestions();
//		int dem = 0;
//		for (Questions questions : arr) {
//			System.out.println(dem++);
//			System.out.println(questions.getAnswer());
//		}
		PositionDAO dao = new PositionDAO();
		ArrayList<Position> arr = dao.getAllPosition();
		for (Position position : arr) {
			System.out.println("haha");
			System.out.println(position.getName());
			System.out.println(position.getArea());
			System.out.println(position.getRequirement());
			System.out.println(position.getBenefit());
			System.out.println(position.getDescription());
			System.out.println(position.getExpDate());
		}
		Position p = dao.getOnePositionByName("Product Manager",arr);
		System.out.println("positon by name: " + p.getName());

	}

}
