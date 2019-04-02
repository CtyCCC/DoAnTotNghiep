package com.manhcuong.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.manhcuong.dao.CandidateDAO;
import com.manhcuong.entity.Candidate;

@Controller
public class TestController {
	
	private CandidateDAO candidateDAO =  new CandidateDAO();
	
	@GetMapping("/")
	public String index(Model model) {
		ArrayList<Candidate> ds_Can = new ArrayList<>();
		ds_Can = candidateDAO.getAllCandidate_S();
		model.addAttribute("dsCandidates", ds_Can);
		return "index";
	}
	
	@GetMapping("/profile.html")
	public String profile() {
		return "profile";
	}
	
	@GetMapping("/position.html")
	public String position() {
		return "position";
	}
}
