package com.position.controller;


import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.position.dao.PositionDAO;
import com.position.dao.QuestionsDAO;
import com.position.entity.Position;
import com.position.entity.Questions;

@Controller
public class PositionController {
	
	QuestionsDAO quesDAO = new QuestionsDAO();
	PositionDAO posDAO = new PositionDAO();
	
	@GetMapping("/position.html")
	public String index(Model model) {
		ArrayList<Questions> arrQues = new ArrayList<>();
		arrQues = quesDAO.getAllQuestions();
		
		model.addAttribute("listQuestions", arrQues);
		
		
		ArrayList<Position> arrPos = new ArrayList<>();
		arrPos = posDAO.getAllPosition();
		Position p = posDAO.getOnePositionByName("Product Manager", arrPos);
		
		model.addAttribute("oneposition",p);
		return "position";
	}

}


