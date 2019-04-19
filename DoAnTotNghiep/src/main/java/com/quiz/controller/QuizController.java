package com.quiz.controller;


import java.io.IOException;
import java.util.ArrayList;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.position.entity.Questions;


import com.quiz.dao.QuizDao;
import com.quiz.form.Result;

@Controller
@RequestMapping(value="quiz")
public class QuizController {
	
	QuizDao quizdao = new QuizDao();
	
	@GetMapping("/quiz.clt")
	public String index(Model model) {
		ArrayList<Questions> list = new ArrayList<Questions>();
		list = quizdao.getAllQuestionsQuiz();
		Result result =new Result();
		model.addAttribute("result",result);
		model.addAttribute("listQuestions", list);
		return "quiz";
	}
	
	@PostMapping("/quiz.clt")
	public ResponseEntity<?> FinishQuiz(@RequestBody ArrayList<Result> data) {
		for (Result result : data) {
			if(!result.getAnswer().equals("on"))
				System.out.println(result.getIdQues()+"-"+result.getAnswer());
			else
				System.out.println("position la"+ result.getIdQues());
		}
		return ResponseEntity.ok("ok");
	}
}