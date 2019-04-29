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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apply.form.TitlePosition;
import com.candidate.dao.CandidateDAO;
import com.candidate.form.FormRound;
import com.entity.Candidate;
import com.entity.Position;
import com.position.dao.PositionDAO;

@Controller
public class TestController {
	//cái này để khỏi tạo new CandidateDAO
	@Autowired
	private CandidateDAO candidateDAO ;
	@Autowired
	private PositionDAO positionDAO ;
	
	PositionDAO posDAO = new PositionDAO();
	

	@GetMapping("/index")
	public String index(Model model) {

		ArrayList<Candidate> ds_Can = new ArrayList<>();
		ds_Can = candidateDAO.getAllCandidate_M();
		model.addAttribute("dsCandidates", ds_Can);

		//get all Positionn để đổ name vô select box
		ArrayList<Position> arrPos = new ArrayList<>();	
		arrPos = posDAO.getAllPosition();
		model.addAttribute("listPosition", arrPos);
		
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
				stus = "Interviewing";
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

	//	@PostMapping("/candidate")
	//	public String can(HttpServletRequest request, RedirectAttributes attr ) {
	//		String id = request.getParameter("id");
	//		//can = candidateDAO.getCandidateById(id);
	//		
	//		attr.addAttribute("IdCan", id);
	//		return "redirect:/login";
	//	}

	@GetMapping("/profile")
	public String profile(Model model, @RequestParam("id") String id){
		Candidate can = candidateDAO.getCandidateById(id);
		if(!(can.getIdCan()==null)) {
			System.out.println(can);
			model.addAttribute("candidate",can);

			//Đổ dữ liệu cho status
			String [] n = {"New","Reject"};
			String[] in = {"Interviewing","Interview Pass","Interview Fail"};
			String [] o = {"Offering","Offer Pass","Offer Fail"};
			String [] p = {"Probation","Probation Fail","probation Pass"};
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
		String id = req.getParameter("id");
		Candidate can = candidateDAO.getCandidateById(id);
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
		String id = request.getParameter("id");
		Candidate can = candidateDAO.getCandidateById(id);
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

	@PostMapping("/profile/selectRound")
	public ResponseEntity<?> showRound(@RequestBody FormRound data) {
		String idRound = data.getRound();
		String idCan = data.getId();
		if(!(idRound.isEmpty())) {
			Map<String, Object> round = candidateDAO.getRoundById(idRound, idCan);
			FormRound fr = new FormRound(idCan, idRound, (String)round.get("interviewer"), (String)round.get("date"), (String)round.get("time"), (String)round.get("note"), (String)round.get("venue"), (String)round.get("result"));
			return ResponseEntity.ok(fr);
		}
		return null;
	}

	@PostMapping("/profile/editRoundInterview")
	public @ResponseBody String editRound(HttpServletRequest request) throws JSONException {
		//Lấy dữ liệu từ ajax gửi về
		String id = request.getParameter("id");
		Candidate can = candidateDAO.getCandidateById(id);
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		String interviewer = request.getParameter("interviewer");
		String venue = request.getParameter("venue");
		String note = request.getParameter("note");
		String result = request.getParameter("result");
		String idr = request.getParameter("idRound");
		ArrayList<Object> rounds=  (ArrayList<Object>) can.getInterview().get("rounds");
		for(int i = 0 ; i<rounds.size();i++) {
			Map<String, Object> r = (Map<String, Object>) rounds.get(i);
			String idRo = (String) r.get("idRound");
			if(idRo.equals(idr)) {
				rounds.remove(i);
				System.out.println("lala");
			}

		}	
		Map<String, Object> rnd = new HashMap<String, Object>();
		rnd.put("idRound",idr);
		rnd.put("interviewer",interviewer);
		rnd.put("date", date);
		rnd.put("time", time);
		rnd.put("venue", venue);
		rnd.put("note", note);
		rnd.put("result", result);	
		rounds.add(rnd);

		candidateDAO.editRoundById(rounds,can.getIdCan(), can.getCmnd());
		return "Edit Round Success";
	}

	@PostMapping("/profile/finishInterview")
	public @ResponseBody String updateFinalResultInterview(HttpServletRequest req) {
		String idCan = req.getParameter("idCan");
		String cmnd = req.getParameter("cmnd");
		String fnRs = req.getParameter("fnRs");
		int non_Rs = 0;
		Candidate can = candidateDAO.getCandidateById(idCan);
		ArrayList<Object> rounds = (ArrayList<Object>) can.getInterview().get("rounds");
		for(int i = 0; i<rounds.size();i++) {
			Map<String, Object> r = (Map<String, Object>) rounds.get(i);
			String rs = (String) r.get("result");
			if(rs.equals("Unknown")) {
				non_Rs++;
				return "Check Result";
			}
		}
		if(non_Rs==0) {
			candidateDAO.updateFinalResult(idCan, cmnd, fnRs);
		}
		return "OK";		
	}
}
