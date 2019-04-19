package com.position.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.position.dao.PositionDAO;
import com.position.dao.QuestionsDAO;
import com.entity.Position;
import com.entity.Questions;

@Controller
public class PositionController {

	QuestionsDAO quesDAO = new QuestionsDAO();
	PositionDAO posDAO = new PositionDAO();
	String nameAjax;
	
	@GetMapping("/position")
	public String index(Model model,HttpServletRequest request) {
		String namePos = request.getParameter("namePos");
		
		//question
		ArrayList<Questions> arrQues = new ArrayList<>();
		arrQues = quesDAO.getAllQuestions();

		model.addAttribute("listQuestions", arrQues);

		//Positionn
		ArrayList<Position> arrPos = new ArrayList<>();	
		arrPos = posDAO.getAllPosition();
		//format arr pos
		posDAO.formatAllResult(arrPos);

		//đổ name vô select box
		model.addAttribute("listPosition", arrPos);
		
		if (nameAjax != null) {
			Position p = posDAO.getOnePositionByName(nameAjax, arrPos);
			model.addAttribute("selectedposition",p);
		}else if(namePos != null) {
			Position p = posDAO.getOnePositionByName(namePos, arrPos);
			model.addAttribute("selectedposition",p);
		}
		else {
			Position p = posDAO.getOnePositionByName("Product Manager", arrPos);
			model.addAttribute("selectedposition",p);
		}
			
		return "position";
	}

	@RequestMapping(value="/loadposition",method=RequestMethod.POST)
	public  @ResponseBody void  getPostionWhenChangeSelectBox( HttpServletRequest request, Model model) {
		System.out.println("After selected: " +nameAjax);
		nameAjax = request.getParameter("name");
		System.out.println("before selected: " +nameAjax);
	}
	@RequestMapping(value="/newQuestion",method=RequestMethod.POST)
	public  @ResponseBody void  addNewQues( HttpServletRequest request, Model model) {
		
		//lấy mảng question để tạo id mới
		ArrayList<Questions> arrQues = new ArrayList<>();
		arrQues = quesDAO.getAllQuestions();
		String idQues = quesDAO.autoIdQues(arrQues);
		String content = request.getParameter("content");
		String a = request.getParameter("sentenceA");
		String b = request.getParameter("sentenceB");
		String c = request.getParameter("sentenceC");
		String d = request.getParameter("sentenceD");
		String answer = request.getParameter("answer");
		Questions q = new Questions(idQues, content, a, b, c, d, answer);
		quesDAO.addNewQues(q);
		System.out.println("Add success question: " +idQues);
	}

}


