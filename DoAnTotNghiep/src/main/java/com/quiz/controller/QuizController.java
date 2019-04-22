package com.quiz.controller;


import java.util.ArrayList;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Candidate;
import com.entity.Questions;


import com.quiz.dao.QuizDao;
import com.quiz.form.QuizForm;

@Controller
public class QuizController {
	
	QuizDao quizdao = new QuizDao();
	
	@GetMapping("/quiz")
	public String index(Model model,@RequestParam("IDPOS") String idpos,@RequestParam("IDCAN") String idcan) {
		System.out.println(idpos);
		ArrayList<Questions> list = new ArrayList<Questions>();
		list = quizdao.getQuestionsQuiz(idpos);
		QuizForm result =new QuizForm();
		model.addAttribute("result",result);
		model.addAttribute("idcan",idcan);
		model.addAttribute("idPos",idpos);
		model.addAttribute("listQuestions", list);
		return "/quiz";
	}
	
	@PostMapping("/quiz")
	public ResponseEntity<?> FinishQuiz(@RequestBody ArrayList<QuizForm> data) {
		for (QuizForm result : data) {
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