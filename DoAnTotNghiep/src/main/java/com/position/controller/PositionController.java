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
		
		//get all question
		ArrayList<Questions> arrQues = new ArrayList<>();
		arrQues = quesDAO.getAllQuestions();

		model.addAttribute("listQuestions", arrQues);

		//get all Positionn
		ArrayList<Position> arrPos = new ArrayList<>();	
		arrPos = posDAO.getAllPosition();

		//đổ name vô select box
		model.addAttribute("listPosition", arrPos);
		
		//lấy 1 position để hiển thị
		if (nameAjax != null) {
			
			Position p = posDAO.getOnePositionByName(nameAjax, arrPos);
			model.addAttribute("selectedposition",p);
			
			//lấy mảng id câu hỏi theo idPos
			ArrayList<Object> arrIdQues = (ArrayList<Object>) posDAO.getQuestionsOfPosition(p.getIdPos());
			//gửi qua client dạng chuỗi cho dễ
			String stringId = "";
			for (Object string : arrIdQues) {
				stringId = stringId + string +",";
			}
			model.addAttribute("listQuesPos",stringId);
		}else if(namePos != null) {
			Position p = posDAO.getOnePositionByName(namePos, arrPos);
			model.addAttribute("selectedposition",p);
			
			//lấy mảng id câu hỏi theo idPos
			ArrayList<Object> arrIdQues = (ArrayList<Object>) posDAO.getQuestionsOfPosition(p.getIdPos());
			//gửi qua client dạng chuỗi cho dễ
			String stringId = "";
			for (Object string : arrIdQues) {
				stringId = stringId + string +",";
			}
			model.addAttribute("listQuesPos",stringId);
		}else {
			Position p = posDAO.getOnePositionByName("Product Manager", arrPos);
			model.addAttribute("selectedposition",p);
			
			//lấy mảng id câu hỏi theo idPos
			ArrayList<Object> arrIdQues = (ArrayList<Object>) posDAO.getQuestionsOfPosition(p.getIdPos());
			//gửi qua client dạng chuỗi cho dễ
			String stringId = "";
			for (Object string : arrIdQues) {
				stringId = stringId + string +",";
			}
			model.addAttribute("listQuesPos",stringId);
		}
		return "position";
	}

	@RequestMapping(value="/loadposition",method=RequestMethod.POST)
	public  @ResponseBody void  getPostionWhenChangeSelectBox( HttpServletRequest request, Model model) {
		System.out.println("After selected: " +nameAjax);
		nameAjax = request.getParameter("name");
		System.out.println("before selected: " +nameAjax);
	}
	
	@RequestMapping(value="/updateListQues",method=RequestMethod.POST)
	public  @ResponseBody void  updateListQuesPos( HttpServletRequest request, Model model) {
		String idPos = request.getParameter("idPos");
		String name = request.getParameter("name");
		String arr =request.getParameter("listQues");
		posDAO.updateQuesPos(idPos, name, arr.toString());	
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
	
	@RequestMapping(value="/newPostion",method=RequestMethod.POST)
	public  @ResponseBody void  addNewPos( HttpServletRequest request, Model model) {
		
		//lấy mảng Pos để tạo id mới
		ArrayList<Position> arrPos = new ArrayList<>();
		arrPos = posDAO.getAllPosition();
		String idPos = posDAO.autoIdPos(arrPos);
		
		String name = request.getParameter("name");
		String area = request.getParameter("area");
		String expDate = request.getParameter("expDate");
		String requirement = request.getParameter("requirement");
		String benefit = request.getParameter("benefit");
		String description = request.getParameter("description");
		String arr =request.getParameter("listQues");
		
		Position p = new Position(idPos, name, area, expDate, requirement, arr.toString(), benefit, description);
		posDAO.addNewPos(p);
		System.out.println("Add success question: " +p.toString());
	}
	
	@RequestMapping(value="/editPosition",method=RequestMethod.POST)
	public  @ResponseBody void editPos( HttpServletRequest request, Model model) {
		
		String name = request.getParameter("name");
		String area = request.getParameter("area");
		String expDate = request.getParameter("expDate");
		String requirement = request.getParameter("requirement");
		String benefit = request.getParameter("benefit");
		String description = request.getParameter("description");
		
		ArrayList<Position> arrPos = new ArrayList<>();
		arrPos = posDAO.getAllPosition();
		Position p = posDAO.getOnePositionByName(name, arrPos);
		
		p.setArea(area);
		p.setExpDate(expDate);
		p.setRequirement(requirement);
		p.setBenefit(benefit);
		p.setDescription(description);
		
		posDAO.updatePos(p);
	}

}


