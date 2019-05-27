package com.quiz.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.apply.dao.ApplyDao;
import com.candidate.dao.CandidateDAO;
import com.entity.Candidate;
import com.entity.Questions;


import com.quiz.dao.QuizDao;
import com.quiz.form.QuizForm;

@Controller
public class QuizController {
	
	QuizDao quizdao = new QuizDao();
	ApplyDao applydao =new ApplyDao();
	CandidateDAO candidatedao =new CandidateDAO();
	
	@GetMapping("/quiz")
	public String index(Model model,@RequestParam("IDPOS") String idpos,@RequestParam("IDCAN") String idcan) {
		//Kiểm tra timestart 
		Candidate can = quizdao.getCandidateById_S(idcan);
		long fulltime = 30 * 60 * 1000;
		long Timeremaining=30;
		if(can.getRate() != null){
			return "homeApply";
			
		}else {
			if(can.getDateImport().toString().equals("not")) {
				quizdao.addTimeStart(can.getIdCan());
			}else {
				Timeremaining = quizdao.Timeremaining(idcan);
				if(Timeremaining <= 0) {
					return "homeApply";
				}
			}
		}
		// get timetstart ==null -> continue else stop
		
		ArrayList<Questions> list = new ArrayList<Questions>();
		list = quizdao.getQuestionsQuiz(idpos);
		QuizForm result =new QuizForm();
		model.addAttribute("result",result);
		model.addAttribute("idcan",idcan);
		model.addAttribute("idPos",idpos);
		model.addAttribute("time",fulltime);
		model.addAttribute("timeremaining",Timeremaining);
		model.addAttribute("listQuestions", list);
		return "quiz";
	}
	
	//set time start
	public ResponseEntity<?> importTimeStart(){
		return ResponseEntity.ok("Time");
	}
	
	@PostMapping("/quizFinish")
	public ResponseEntity<?> FinishQuiz(@RequestBody ArrayList<QuizForm> data) {
		String idC ="";
		String time= "";
		int score = 0;
		int total = 0;
		try {
		for (QuizForm result : data) {
			if(!result.getIdQues().equals("idPos") && !result.getIdQues().equals("idCan") && !result.getIdQues().equals("time")) {
					if(quizdao.CheckQuest(result.getIdQues(), result.getAnswer()))
						score++;
			}
			else
				if(result.getIdQues().equals("idPos"))
				{
					total = quizdao.getTotalQuestofPpsition(result.getAnswer());
				}
				else if(result.getIdQues().equals("idCan"))
				{
					idC = result.getAnswer();
				}
				else if(result.getIdQues().equals("time")) {
					time = result.getAnswer();
				}				
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Map<String, Object> infoMap =new HashMap<String, Object>();
		infoMap.put("score",score);
		infoMap.put("total",total);
		infoMap.put("time",time);
		Candidate can = quizdao.getCandidateById_S(idC);
		applydao.updateRateofCadidate(can, infoMap);
		return ResponseEntity.ok("ok");
	}
}