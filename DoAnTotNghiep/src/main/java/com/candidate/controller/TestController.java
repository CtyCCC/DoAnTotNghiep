package com.candidate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.candidate.dao.CandidateDAO;
import com.entity.Candidate;

@Controller
public class TestController {
	
	private CandidateDAO candidateDAO =  new CandidateDAO();
	Candidate can = new Candidate();
	
	@GetMapping("/index")
	public String index(Model model) {
		ArrayList<Candidate> ds_Can = new ArrayList<>();
		ds_Can = candidateDAO.getAllCandidate_M();
		model.addAttribute("dsCandidates", ds_Can);
		return "index";
	}
	
	@PostMapping("/index/setInterview")
	public @ResponseBody String itv(HttpServletRequest request) {
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		String interviewer = request.getParameter("interviewer");
		String venue = request.getParameter("venue");
		String note = request.getParameter("note");
		String idCan = request.getParameter("idCan");
		
		
//		Candidate can = new Candidate();
//		can = candidateDAO.getCandidateById(idCan);
		ArrayList<Object> rounds = new ArrayList<>();
//		can.getInterview().get("rounds");
		
		Map<String, Object> rnd = new HashMap<String, Object>();
		rnd.put("idRound","1");
		rnd.put("interviewer",interviewer);
		rnd.put("date", date);
		rnd.put("time", time);
		rnd.put("venue", venue);
		rnd.put("note", note);
		rnd.put("result", "Unknown");
		
		rounds.add(rnd);
		
		candidateDAO.addInterview("can_2", "222222222", rounds);
		
		//System.out.println(date+time+interviewer+venue);
		return "Set Interview Success";
	}
	
	@PostMapping("/candidate")
	public @ResponseBody void can(HttpServletRequest request) {
		String id = request.getParameter("id");
		can = candidateDAO.getCandidateById(id);
	}
	
	@GetMapping("/profile")
	public String profile(Model model) {		
		if(!(can.getIdCan()==null)) {
			System.out.println(can);
			model.addAttribute("candidate",can);
			return "profile";
		}
		return null;
	}
	
}
