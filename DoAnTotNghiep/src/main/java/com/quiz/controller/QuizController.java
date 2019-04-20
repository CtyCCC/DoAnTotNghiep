package com.quiz.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Questions;


import com.quiz.dao.QuizDao;
import com.quiz.form.Result;

@Controller
public class QuizController {
	
	QuizDao quizdao = new QuizDao();
	
	@GetMapping("/quiz")
	public String index(Model model) {
		ArrayList<Questions> list = new ArrayList<Questions>();
		ArrayList<Questions> listtest = new ArrayList<Questions>();
		list = quizdao.getQuestionsQuiz("POS10");
		System.out.println(listtest);
		Result result =new Result();
		model.addAttribute("result",result);
		model.addAttribute("listQuestions", list);
		return "quiz";
	}
	
	@PostMapping("/quiz")
	public ResponseEntity<?> FinishQuiz(@RequestBody ArrayList<Result> data) {
		for (Result result : data) {
			if(!result.getIdQues().equals("idPos") || !result.getIdQues().equals("idCan"))
				System.out.println(result.getIdQues()+"-"+result.getAnswer());
			else
				if(result.getIdQues().equals("idPos"))
					System.out.println("position la: "+ result.getAnswer());
				else
					System.out.println("Candidate la: "+ result.getAnswer());
		}
		return ResponseEntity.ok("ok");
	}
}