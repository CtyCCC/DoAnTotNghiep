package com.position.dao;

import java.util.ArrayList;

import com.entity.Position;
import com.entity.Questions;

public class TestQuessss {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		QuestionsDAO dao = new QuestionsDAO();
//		Questions qq = new Questions("idTesst44244555", "contenttest244444555", "a", "b", "c", "d", "a");
//		dao.addNewQues(qq);
		ArrayList<Questions> arr =dao.getAllQuestions();
		int dem = 0;
		for (Questions questions : arr) {
			System.out.println(dem++);
			System.out.println(questions.getIdQues());
		}
		System.out.println("id moi ne: "+dao.autoIdQues(arr));
//		PositionDAO dao = new PositionDAO();
//		ArrayList<Position> arr = dao.getAllPosition();
//		dao.formatAllResult(arr);
//		for (Position position : arr) {
//			System.out.println("haha");
//			System.out.println(position.getName());
//			System.out.println(position.getArea());
//			System.out.println(position.getRequirement());
//			System.out.println(position.getBenefit());
//			System.out.println(position.getDescription());
//			System.out.println(position.getExpDate());
//		}
//		Position p = dao.getOnePositionByName("Product Manager",arr);
//		System.out.println("positon by name: " + p.getName());
//		String a = " Good at basic programming knowledge: OOP- Experience in Java core, HTML, CSS, Javascript- Ability to work with database such as: Oracle, My SQL, SQL Server- Understanding about Web API, Collection, Interface, Abstract Class, Bootstrap, jQuery, Design Pattern, LINQ...- Nice to know Angular JS";
//		System.out.println(dao.formatContent(a));

	}

}
