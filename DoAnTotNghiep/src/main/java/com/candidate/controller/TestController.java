package com.candidate.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.candidate.dao.CandidateDAO;
import com.candidate.entity.Candidate;

@Controller
public class TestController {
	
	private CandidateDAO candidateDAO =  new CandidateDAO();
	Candidate can = new Candidate();
	
	@GetMapping("/")
	public String index(Model model) {
		ArrayList<Candidate> ds_Can = new ArrayList<>();
		ds_Can = candidateDAO.getAllCandidate_M();
		model.addAttribute("dsCandidates", ds_Can);
		return "index";
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
	
	@GetMapping("/position")
	public String position(HttpServletRequest request) {
		String id = request.getParameter("id");
		System.out.println(id);
		return "position";
	}
	
}
