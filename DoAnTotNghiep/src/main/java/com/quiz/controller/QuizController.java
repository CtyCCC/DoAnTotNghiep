package com.quiz.controller;


import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Questions;
import com.quiz.dao.QuizDAO;

@Controller
@RequestMapping(value="apply")
public class QuizController {
	
	QuizDAO quizdao = new QuizDAO();
	
	@GetMapping("/quiz.html")
	public String index(Model model) {
		ArrayList<Questions> list = new ArrayList<Questions>();
		list = quizdao.getAllQuestionsQuiz();
		model.addAttribute("listQuestions", list);
		return "quiz";
	}
}


