package com.position.controller;


import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.position.dao.QuestionsDAO;
import com.position.entity.Questions;

@Controller
public class PositionController {
	
	QuestionsDAO quesDAO = new QuestionsDAO();
	
	@GetMapping("/position.html")
	public String index(Model model) {
		ArrayList<Questions> arrQues = new ArrayList<>();
		arrQues = quesDAO.getAllQuestions();
		model.addAttribute("listQuestions", arrQues);
		return "position";
	}
	
	@RequestMapping(value = "/reservation2", method = RequestMethod.POST)
	public String reservation2(@RequestBody String idt) 
	{
	    // your logic here
		System.out.println(idt);
	    return "reservation2";
	}
}


