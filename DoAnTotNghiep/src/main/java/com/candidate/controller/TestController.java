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
import com.entity.Position;
import com.position.dao.PositionDAO;

@Controller
public class TestController {
	
	private CandidateDAO candidateDAO =  new CandidateDAO();
	private PositionDAO positionDAO = new PositionDAO();
	Candidate can = new Candidate();
	
	@GetMapping("/index")
	public String index(Model model) {
		ArrayList<Candidate> ds_Can = new ArrayList<>();
		ds_Can = candidateDAO.getAllCandidate_M();
		model.addAttribute("dsCandidates", ds_Can);
		return "index";
	}
	
	@PostMapping("/index/setInterview")
	public @ResponseBody String setInterview(HttpServletRequest request) throws JSONException {
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
            
            String stus = "Wait for interview";
            
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
	    		
	    		candidateDAO.addInterview(idCan, cmnd, rounds, stus);
			}else {
				if(!(can.getInterview().get("finalResult")).equals("Unknown")) {
					stus = "Interviewing";
				}
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
	    		
	    		candidateDAO.addInterview(idCan, cmnd, rounds,stus);
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
			
			//Đổ dữ liệu cho status
			String [] n = {"New","Reject"};
			String[] in = {"Interviewing","Interview Pass","Interview Fail"};
			String [] o = {"Offering","Offer Pass","Offer Fail"};
			String [] p = {"Probationing","Probation Fail","probation Pass"};
			if(can.getInterview()==null) {
				model.addAttribute("stt",n);
			}else if(can.getOffer()==null) {
				model.addAttribute("stt",in);
			}else if(can.getProbation()==null) {
				model.addAttribute("stt",o);
			}else {
				model.addAttribute("stt",p);
			}
			
			
			//Đổ dữ liệu cho Position Apply
			ArrayList<String> dsNamePos = new ArrayList<>();
			ArrayList<Position> pos = new ArrayList<Position>();
			pos = positionDAO.getAllPosition();
			for(int i = 0; i<pos.size();i++) {
				dsNamePos.add(pos.get(i).getName());
			}
			model.addAttribute("dsNamePos", dsNamePos);
			
			
			//Đổ dữ liệu cho tag Interview
			int pass = 0;
			int fail = 0;
			int totalRound = 0;
			String isIn = "Not";
			String[] fr = {"Unknown", "Pass","Fail"};
			
			if(can.getInterview()!=null) {
				isIn = (String) can.getInterview().get("finalResult");
				ArrayList<Object> rounds=  (ArrayList<Object>) can.getInterview().get("rounds");
				totalRound = rounds.size();
				
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
				model.addAttribute("dsR", dsR);
			}
			model.addAttribute("TotalRound", totalRound);
			model.addAttribute("Pass", pass);
			model.addAttribute("Fail", fail);
			model.addAttribute("fr", fr);
			model.addAttribute("isIn",isIn);
			
			
			
			
			return "profile";
		}
		return null;
	}
	
	@PostMapping("/profile/editProfile")
	public @ResponseBody String editProfile(HttpServletRequest req) {
		can.setNameCan(req.getParameter("name"));
		can.setEmail(req.getParameter("email"));
		can.setNamePos(req.getParameter("namePos"));
		can.setDob(req.getParameter("dob"));
		can.setPhone(req.getParameter("phone"));
		if(req.getParameter("gender").equals("true")) {
			can.setGender(true);
		}else
			can.setGender(false);
		can.setDateImport(req.getParameter("dateImp"));
		can.setWorkExp(req.getParameter("workExp"));
		Map<String,Object> rate = new HashMap<>();
		if(req.getParameter("score")!=null && req.getParameter("time")!=null && req.getParameter("total")!=null) {
			System.out.println("ok");
			rate.put("score", Integer.parseInt(req.getParameter("score")));
			rate.put("time", Integer.parseInt(req.getParameter("time")));
			rate.put("total", Integer.parseInt(req.getParameter("total")));
			can.setRate(rate);
		}
		candidateDAO.editProfile(can);
		return "Edit Success";
	}
	
	@PostMapping("/profile/addRoundInterview")
	public @ResponseBody String addRound(HttpServletRequest request) throws JSONException {
		//Lấy dữ liệu từ ajax gửi về
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		String interviewer = request.getParameter("interviewer");
		String venue = request.getParameter("venue");
		String note = request.getParameter("note");
		String result = request.getParameter("result");
		
		String stus = "Wait for interview";
        
		if(can.getInterview()==null) {
			ArrayList<Object> rounds = new ArrayList<>();
			Map<String, Object> rnd = new HashMap<String, Object>();
    		rnd.put("idRound",rounds.size()+1+"");
    		rnd.put("interviewer",interviewer);
    		rnd.put("date", date);
    		rnd.put("time", time);
    		rnd.put("venue", venue);
    		rnd.put("note", note);
    		rnd.put("result", result);	
    		rounds.add(rnd);
    		
    		candidateDAO.addInterview(can.getIdCan(), can.getCmnd(), rounds, stus);
		}else {
			ArrayList<Object> rounds=  (ArrayList<Object>) can.getInterview().get("rounds");
			for(int i = 0 ; i<rounds.size();i++) {
				Map<String, Object> r = (Map<String, Object>) rounds.get(i);
				if(!(r.get("result")).equals("Unknown")) {
					stus = "Interviewing";
				}
			}
            Map<String, Object> rnd = new HashMap<String, Object>();
    		rnd.put("idRound",rounds.size()+1+"");
    		rnd.put("interviewer",interviewer);
    		rnd.put("date", date);
    		rnd.put("time", time);
    		rnd.put("venue", venue);
    		rnd.put("note", note);
    		rnd.put("result", result);	
    		rounds.add(rnd);
    		
    		candidateDAO.addInterview(can.getIdCan(), can.getCmnd(), rounds,stus);
		}			
			
		return "Add Round Success";
	}
}
