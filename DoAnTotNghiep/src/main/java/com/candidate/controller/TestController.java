package com.candidate.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
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
	public @ResponseBody String itv(HttpServletRequest request) throws JSONException {
		//Lấy dữ liệu từ ajax gửi về
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		String interviewer = request.getParameter("interviewer");
		String venue = request.getParameter("venue");
		String note = request.getParameter("note");
		JSONArray arr = new JSONArray(request.getParameter("id_cmnd"));
		
		//Thêm interview
		for (int i = 0; i < arr.length(); i++) {
            String idCan = arr.getJSONObject(i).getString("idCan");
            //System.out.println(idCan);
            String cmnd = arr.getJSONObject(i).getString("cmnd");
            //System.out.println(cmnd);
            
			Candidate can = new Candidate();
			can = candidateDAO.getCandidateById(idCan);
			if(can.getInterview()==null) {
				ArrayList<Object> rounds = new ArrayList<>();
				Map<String, Object> rnd = new HashMap<String, Object>();
	    		rnd.put("idRound",rounds.size()+1+"");
	    		rnd.put("interviewer",interviewer);
	    		rnd.put("date", date);
	    		rnd.put("time", time);
	    		rnd.put("venue", venue);
	    		rnd.put("note", note);
	    		rnd.put("result", "Unknown");	
	    		rounds.add(rnd);
	    		
	    		candidateDAO.addInterview(idCan, cmnd, rounds);
			}else {
				ArrayList<Object> rounds=  (ArrayList<Object>) can.getInterview().get("rounds");
	            Map<String, Object> rnd = new HashMap<String, Object>();
	    		rnd.put("idRound",rounds.size()+1+"");
	    		rnd.put("interviewer",interviewer);
	    		rnd.put("date", date);
	    		rnd.put("time", time);
	    		rnd.put("venue", venue);
	    		rnd.put("note", note);
	    		rnd.put("result", "Unknown");	
	    		rounds.add(rnd);
	    		
	    		candidateDAO.addInterview(idCan, cmnd, rounds);
			}			
        }
			
		return "Set Interview Success";
	}
	
	@PostMapping("/index/deleteCandidate")
	public @ResponseBody String deleteCan(HttpServletRequest request) throws JSONException{
		JSONArray arr = new JSONArray(request.getParameter("id_cmnd"));
		for (int i = 0; i < arr.length(); i++) {
			String idCan = arr.getJSONObject(i).getString("idCan");
            String cmnd = arr.getJSONObject(i).getString("cmnd");
            
            candidateDAO.deleteCandidate(idCan, cmnd);
		}
		return "Delete Candidates Success";		
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
			if(can.getInterview()!=null) {
				ArrayList<Object> rounds=  (ArrayList<Object>) can.getInterview().get("rounds");
				int totalRound = rounds.size();
				int pass = 0;
				int fail = 0;
				String[] fr = {"Unknown", "Pass","Fail"};
				ArrayList<String> dsR = new ArrayList<>();
				for(int i = 0; i<rounds.size(); i++) {
					Map<String, Object> rnd = new HashMap<String, Object>();
					rnd = (Map<String, Object>) rounds.get(i);
					if(rnd.get("result").equals("Pass")) {
						pass++;
					}
					if(rnd.get("result").equals("Fail")) {
						fail++;
					}
					dsR.add("Round "+(i+1));
				}
				model.addAttribute("TotalRound", totalRound);
				model.addAttribute("Pass", pass);
				model.addAttribute("Fail", fail);
				model.addAttribute("fr", fr);
				model.addAttribute("dsR", dsR);
			}
			
			return "profile";
		}
		return null;
	}
	
}
