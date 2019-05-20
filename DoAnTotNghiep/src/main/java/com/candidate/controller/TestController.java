package com.candidate.controller;


import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.usermanagement.dao.UserDAO;

@Controller
public class TestController {
	//cái này để khỏi tạo new CandidateDAO
	@Autowired
	private CandidateDAO candidateDAO ;
	@Autowired
	private PositionDAO positionDAO ;
	@Autowired
	private UserDAO userDAO;

	PositionDAO posDAO = new PositionDAO();

	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("H:mm:ss");

	@GetMapping("/index")
	public String index(Model model, Principal principal) {
		//Lấy thông tin user
		String userName = principal.getName();
		String name = "Unknown";
		if(userName != null) {
			name = userDAO.getUserByUserName(userName).getName();

		}
		model.addAttribute("userName", userName);
		model.addAttribute("fullName", name);

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
	public @ResponseBody String setInterview(HttpServletRequest request, Principal principal) throws JSONException {
		//Lấy thông tin user
		String userName = principal.getName();

		//Lấy dữ liệu từ ajax gửi về
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		String interviewer = request.getParameter("interviewer");
		String venue = request.getParameter("venue");
		String note = request.getParameter("note");
		JSONArray arr = new JSONArray(request.getParameter("id_cmnd"));

		if(note.equals("")) {
			note = "Nothing";
		}

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
			}else if(can.getInterview() != null && can.getStatus().equals("Interviewing")){
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

			//Lưu log
			ArrayList<Object> logs = new ArrayList<>();
			logs = candidateDAO.getAllLog(idCan);
			Map<String, Object> log = new HashMap<String, Object>();
			log.put("by", userName);
			log.put("date", java.time.LocalDate.now().format(formatter1));
			log.put("time", java.time.LocalTime.now().format(formatter2));
			log.put("method", "Set Interview");
			log.put("change", "Not");
			logs.add(0,log);
			candidateDAO.addLog(idCan, cmnd, logs);
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
	public String profile(Model model, @RequestParam("id") String id, Principal principal){
		//Lấy thông tin user
		String userName = principal.getName();
		String name = "Unknown";
		if(userName != null) {
			name = userDAO.getUserByUserName(userName).getName();

		}
		model.addAttribute("userName", userName);
		model.addAttribute("fullName", name);


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

			//Đổ dữ liệu cho Offer
			String isOf = "Not";
			String [] ofRs = {"Unknown","Accept", "Decline"};
			if(can.getOffer()!=null) {
				isOf = (String) can.getOffer().get("result");
			}

			//Đổ dữ liệu cho Probation
			String isPro = "Not";
			if(can.getProbation()!=null) {
				isPro = (String) can.getProbation().get("result");
			}

			//Đổ dữ liệu cho logs
			ArrayList<Object> logs = candidateDAO.getAllLog(id);
			ArrayList<String> dsDate = new ArrayList<>();
			Set inputSet = new HashSet(); //Dùng để kiểm tra phần tử trùng (thay cho việc dùng for)
			if(logs !=null ) {
				for(int k = 0; k < logs.size();k++) {
					Map<String, Object> log = (Map<String, Object>) logs.get(k);
					String date = (String) log.get("date");
					if(inputSet.add(date)) { //inputSet chỉ cho thêm phần tử ko trùng
						dsDate.add(date);
					}
				}
				System.out.println(dsDate);
				model.addAttribute("dsDate", dsDate);
				model.addAttribute("logs", logs);
			}

			model.addAttribute("TotalRound", totalRound);
			model.addAttribute("Pass", pass);
			model.addAttribute("Fail", fail);
			model.addAttribute("fr", fr);
			model.addAttribute("isIn",isIn);
			model.addAttribute("ofRs", ofRs);
			model.addAttribute("isOf",isOf);
			model.addAttribute("isPro", isPro);
			return "profile";
		}
		return null;
	}

	@PostMapping("/profile/editProfile")
	public @ResponseBody String editProfile(HttpServletRequest req,Principal principal) {
		//Lấy thông tin user
		String userName = principal.getName();	

		String change = "";
		String id = req.getParameter("id");
		Candidate can = candidateDAO.getCandidateById(id);

		String oldName = can.getNameCan();
		if(!oldName.equals(req.getParameter("name"))) {
			change = change + "-Name: "+oldName +" ==> "+req.getParameter("name")+"\n";
		}

		String oldEmail = can.getEmail();
		if(!oldEmail.equals(req.getParameter("email"))) {
			change = change + "-Email: "+oldEmail +" ==> "+req.getParameter("email")+"\n";
		}

		String oldPos = can.getNamePos();
		if(!oldPos.equals(req.getParameter("namePos"))){
			change = change + "-Position Apply: "+oldPos +" ==> "+req.getParameter("namePos")+"\n";
		}

		String oldDOB = can.getDob();
		if(!oldDOB.equals(req.getParameter("dob"))) {
			change = change + "-Position Apply: "+oldPos +" ==> "+req.getParameter("dob")+"\n";
		}

		String oldPhone = can.getPhone();
		if(!oldPhone.equals(req.getParameter("phone"))) {
			change = change + "-Gender: "+oldPhone +"==>"+req.getParameter("phone")+"\n";
		}

		String oldDateImp = can.getDateImport();
		if(!oldDateImp.equals(req.getParameter("dateImp"))) {
			change = change + "-Date Import: "+oldDateImp +" ==> "+req.getParameter("dateImp")+"\n";
		}

		String oldExp = can.getWorkExp();
		if(!oldExp.equals(req.getParameter("workExp"))) {
			change = change + "-Work Exp: "+oldExp +" ==> "+req.getParameter("workExp")+"\n";
		}
		String oldGender = can.isGender()+"";
		if(!oldGender.equals(req.getParameter("gender"))) {
			change = change + "-Gender: "+oldGender +" ==> "+req.getParameter("gender")+"\n";
		}

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

		//Lưu log
		if(!change.equals("")) {
			ArrayList<Object> logs = new ArrayList<>();
			logs = candidateDAO.getAllLog(id);
			Map<String, Object> log = new HashMap<String, Object>();
			log.put("by", userName);
			log.put("date", java.time.LocalDate.now().format(formatter1));
			log.put("time", java.time.LocalTime.now().format(formatter2));
			log.put("method", "Edit Profile");
			log.put("change", change);
			logs.add(0,log);
			candidateDAO.addLog(id, can.getCmnd(), logs);
		}


		return "Edit Success";
	}

	@PostMapping("/profile/addRoundInterview")
	public @ResponseBody String addRound(HttpServletRequest request, Principal principal) throws JSONException {
		//Lấy thông tin user
		String userName = principal.getName();

		//Lấy dữ liệu từ ajax gửi về
		String id = request.getParameter("id");
		Candidate can = candidateDAO.getCandidateById(id);
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		String interviewer = request.getParameter("interviewer");
		String venue = request.getParameter("venue");
		String note = request.getParameter("note");
		String result = request.getParameter("result");
		if(note.isEmpty()) {
			note = "Nothing";
		}

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

			//Lưu log
			ArrayList<Object> logs = candidateDAO.getAllLog(id);		
			Map<String, Object> log = new HashMap<String, Object>();
			log.put("by", userName);
			log.put("date", java.time.LocalDate.now().format(formatter1));
			log.put("time", java.time.LocalTime.now().format(formatter2));
			log.put("method", "Add Round "+rounds.size());
			log.put("change", "Not");
			logs.add(0,log);
			candidateDAO.addLog(id, can.getCmnd(), logs);
			return rounds.size()+"";
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

			//Lưu log
			ArrayList<Object> logs = candidateDAO.getAllLog(id);		
			Map<String, Object> log = new HashMap<String, Object>();
			log.put("by", userName);
			log.put("date", java.time.LocalDate.now().format(formatter1));
			log.put("time", java.time.LocalTime.now().format(formatter2));
			log.put("method", "Add Round "+rounds.size());
			log.put("change", "Not");
			logs.add(0,log);
			candidateDAO.addLog(id, can.getCmnd(), logs);
			return rounds.size()+"";
		}


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
	public @ResponseBody String editRound(HttpServletRequest request, Principal principal) throws JSONException {
		//Lấy thông tin user
		String userName = principal.getName();

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

		String change = "";

		int pass = 0;
		int fail = 0;
		ArrayList<Object> rounds=  (ArrayList<Object>) can.getInterview().get("rounds");
		for(int i = 0 ; i<rounds.size();i++) {
			Map<String, Object> r = (Map<String, Object>) rounds.get(i);
			String idRo = (String) r.get("idRound");
			if(idRo.equals(idr)) {

				//Kiểm tra thay đổi
				String oldDate = (String) r.get("date");
				if(!oldDate.equals(date)) {
					change = change + "-Date: "+oldDate +" ==> "+date+"\n";
				}

				String oldTime = (String) r.get("time");
				if(!oldTime.equals(time)) {
					change = change + "-Time: "+oldTime +" ==> "+time+"\n";
				}

				String oldInterviewer = (String) r.get("interviewer");
				if(!oldInterviewer.equals(interviewer)) {
					change = change + "-Interviewer: "+oldInterviewer +" ==> "+interviewer+"\n";
				}

				String oldVenue = (String) r.get("venue");
				if(!oldVenue.equals(venue)) {
					change = change + "-Venue: "+oldVenue +" ==> "+venue+"\n";
				}

				String oldNote = (String) r.get("note");
				if(!oldNote.equals(note)) {
					change = change + "-Note: "+oldNote +" ==> "+note+"\n";
				}

				String oldRs = (String) r.get("result");
				if(!oldRs.equals(result)) {
					change = change + "-Result: "+oldRs +" ==> "+result+"\n";
				}

				rounds.remove(i);
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

		for(int i = 0 ; i<rounds.size();i++) {
			Map<String, Object> r = (Map<String, Object>) rounds.get(i);
			if(r.get("result").equals("Pass")) {
				pass++;
			}else if(r.get("result").equals("Fail")) {
				fail++;
			}
		}

		//Lưu log
		if(!change.equals("")) {
			ArrayList<Object> logs = new ArrayList<>();
			logs = candidateDAO.getAllLog(id);
			Map<String, Object> log = new HashMap<String, Object>();
			log.put("by", userName);
			log.put("date", java.time.LocalDate.now().format(formatter1));
			log.put("time", java.time.LocalTime.now().format(formatter2));
			log.put("method", "Edit Round "+ idr);
			log.put("change", change);
			logs.add(0,log);
			candidateDAO.addLog(id, can.getCmnd(), logs);
		}

		candidateDAO.editRoundById(rounds,can.getIdCan(), can.getCmnd());

		String s = rounds.size()+","+pass+","+fail;


		return s;
	}

	@PostMapping("/profile/finishInterview")
	public @ResponseBody String updateFinalResultInterview(HttpServletRequest req, Principal principal) {
		//Lấy thông tin user
		String userName = principal.getName();

		String idCan = req.getParameter("idCan");
		String cmnd = req.getParameter("cmnd");
		String fnRs = req.getParameter("fnRs");
		String change = "";
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
			String oldFinalRs = (String) can.getInterview().get("finalResult");
			if(!oldFinalRs.equals(fnRs)) {
				change = change + "-FinalResult: "+oldFinalRs +" ==> "+fnRs+"\n"; 
			}
			candidateDAO.updateFinalResult(idCan, cmnd, fnRs);

			//Lưu log
			if(!change.equals("")) {
				ArrayList<Object> logs = new ArrayList<>();
				logs = candidateDAO.getAllLog(idCan);
				Map<String, Object> log = new HashMap<String, Object>();
				log.put("by", userName);
				log.put("date", java.time.LocalDate.now().format(formatter1));
				log.put("time", java.time.LocalTime.now().format(formatter2));
				log.put("method", "Update FinalResult");
				log.put("change", change);
				logs.add(0,log);
				candidateDAO.addLog(idCan, can.getCmnd(), logs);
			}
		}
		return "OK";		
	}

	@PostMapping("/profile/saveOffer")
	public @ResponseBody String saveOffer (HttpServletRequest req, Principal principal) {
		//Lấy thông tin user
		String userName = principal.getName();

		String id = req.getParameter("id");
		String cmnd = candidateDAO.getCandidateById(id).getCmnd();
		String curSal = req.getParameter("curSal");
		String expectSal = req.getParameter("expecSal");
		String offSal = req.getParameter("offSal");
		String result = req.getParameter("rs");
		String stus = "Offering";
		if(result.equals("Accept")) {
			stus = "Offer Pass";
		}
		if(result.equals("Decline")) {
			stus = "Offer Fail";
		}

		//Kiem tra change
		String change = "";
		Candidate can = candidateDAO.getCandidateById(id);
		String oldCurSal="";
		String oldExpecSal="";
		String oldOffSal="";
		String oldRs="";

		if(can.getOffer()!=null) {
			oldCurSal = can.getOffer().get("curSalary")+"";
			oldExpecSal = can.getOffer().get("expectSalary")+"";
			oldOffSal = can.getOffer().get("offerSalary")+"";
			oldRs = (String)can.getOffer().get("result");
		}

		if(!oldCurSal.equals(curSal)) {
			change = change + "-Curent Salary: "+oldCurSal +" ==> "+curSal+"\n";
		}

		if(!oldExpecSal.equals(expectSal)) {
			change = change + "-Expected Salary: "+oldExpecSal +" ==> "+expectSal+"\n";
		}

		if(!oldOffSal.equals(offSal)) {
			change = change + "-Offer Salary: "+oldOffSal +" ==> "+offSal+"\n";
		}

		if(!oldRs.equals(result)) {
			change = change + "-Result: "+oldRs +" ==> "+result+"\n";
		}


		candidateDAO.editOffer(id, cmnd, stus, curSal, expectSal, offSal, result);

		//Lưu log
		if(!change.equals("")) {
			ArrayList<Object> logs = new ArrayList<>();
			logs = candidateDAO.getAllLog(id);
			Map<String, Object> log = new HashMap<String, Object>();
			log.put("by", userName);
			log.put("date", java.time.LocalDate.now().format(formatter1));
			log.put("time", java.time.LocalTime.now().format(formatter2));
			log.put("method", "Update Offer");
			log.put("change", change);
			logs.add(0,log);
			candidateDAO.addLog(id, can.getCmnd(), logs);
		}

		return "Ok";

	}

	@PostMapping("/profile/saveProbation")
	public @ResponseBody String saveProbation (HttpServletRequest req, Principal principal) {
		//Lấy thông tin user
		String userName = principal.getName();

		String id = req.getParameter("id");
		String cmnd = candidateDAO.getCandidateById(id).getCmnd();
		String dateRange = req.getParameter("dateRange");
		String note = req.getParameter("note");
		String result = req.getParameter("rs");
		String stus = "Probation";
		if(result.equals("Pass")) {
			stus = "Probation Pass";
		}
		if(result.equals("Fail")) {
			stus = "Probation Fail";
		}

		//Kiem tra change
		String change = "";
		Candidate can = candidateDAO.getCandidateById(id);
		String oldDateRange = "";
		String oldRs ="";
		String oldNote="";

		if(can.getProbation()!=null) {
			oldDateRange = (String) can.getProbation().get("dateRange");
			oldRs = (String) can.getProbation().get("result");
			oldNote = (String) can.getProbation().get("note");
		}

		if(!oldDateRange.equals(dateRange)) {
			change = change + "-Date Range: "+oldDateRange +" ==> "+dateRange+"\n";
		}

		if(!oldNote.equals(note)) {
			change = change + "-Note: "+oldNote +" ==> "+note+"\n";
		}

		if(!oldRs.equals(result)) {
			change = change + "-Result: "+oldRs +" ==> "+result+"\n";
		}

		candidateDAO.editProbation(id, cmnd, stus, dateRange, result, note);

		//Lưu log
		if(!change.equals("")) {
			ArrayList<Object> logs = new ArrayList<>();
			logs = candidateDAO.getAllLog(id);
			Map<String, Object> log = new HashMap<String, Object>();
			log.put("by", userName);
			log.put("date", java.time.LocalDate.now().format(formatter1));
			log.put("time", java.time.LocalTime.now().format(formatter2));
			log.put("method", "Update Probation");
			log.put("change", change);
			logs.add(0,log);
			candidateDAO.addLog(id, can.getCmnd(), logs);
		}
		return "Ok";
	}

	@PostMapping("/import")
	public @ResponseBody String importCandidate(HttpServletRequest req, Principal principal) {
		//Lấy thông tin user
		String userName = principal.getName();

		String namePos = req.getParameter("pos");
		int quantity = Integer.parseInt(req.getParameter("quan"));
		int score = Integer.parseInt(req.getParameter("rate"));

		ArrayList<Candidate> dsCan = candidateDAO.getImportCandidate_S(namePos, score, quantity);
		for(Candidate can : dsCan) {
			candidateDAO.addCandidate(can, "Candidate_M");
			
			//Lưu logs
			ArrayList<Object> logs = new ArrayList<>();
			Map<String, Object> log = new HashMap<String, Object>();
			log.put("by", userName);
			log.put("date", java.time.LocalDate.now().format(formatter1));
			log.put("time", java.time.LocalTime.now().format(formatter2));
			log.put("method", "Import into System");
			log.put("change", "Not");
			logs.add(0,log);
			candidateDAO.addLog(can.getIdCan(), can.getCmnd(), logs);
		}
		
		return "OK";
	}
	
	@PostMapping("/addNewCandidate")
	public @ResponseBody String addNewCandidate(HttpServletRequest req, Principal principal) {
		//Lấy thông tin user
		String userName = principal.getName();
		
		String idCan = "CAN"+candidateDAO.getAllCandidate_S().size();
		String nameCan = req.getParameter("name");
		String cmnd = req.getParameter("cmnd");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		String posName = req.getParameter("posName");
		String dob = req.getParameter("dob");
		int score = Integer.parseInt(req.getParameter("score"));
		int total = Integer.parseInt(req.getParameter("total"));
		int time = Integer.parseInt(req.getParameter("time"));
		boolean gender = true;
		if(req.getParameter("gender").equals("false"))
			gender = false;
		String workExp = req.getParameter("workExp");
		String status = req.getParameter("stt");
		
		HashMap<String, Object> rate = new HashMap<>();
		rate.put("score", score);
		rate.put("time", time);
		rate.put("total", total);
		Candidate newCan = new Candidate(idCan, nameCan, cmnd, email, phone, gender, dob, "aaa", posName, java.time.LocalDate.now().format(formatter1), workExp, "bbb", rate, status, null,null,null);
		candidateDAO.addCandidate(newCan, "Candidate_M");
		
		//Lưu log
		ArrayList<Object> logs = new ArrayList<>();
		Map<String, Object> log = new HashMap<String, Object>();
		log.put("by", userName);
		log.put("date", java.time.LocalDate.now().format(formatter1));
		log.put("time", java.time.LocalTime.now().format(formatter2));
		log.put("method", "Import into System");
		log.put("change", "Not");
		logs.add(0,log);
		candidateDAO.addLog(newCan.getIdCan(), newCan.getCmnd(), logs);
		
		return "Ok";
	}
}
