package com.apply.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apply.dao.ApplyDao;
import com.apply.form.FormApply;
import com.apply.form.TitlePosition;
import com.candidate.dao.CandidateDAO;
import com.entity.Candidate;
import com.entity.Position;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.InputStreamContent;
import com.subsystem.uploadfile.GGDrive;

@Controller
public class ApplyController {

	ApplyDao applydao = new ApplyDao();
	CandidateDAO candidatedao = new CandidateDAO();
	GGDrive GG =new GGDrive();


	// Lấy thông tin tổng quát(id,tên,ngày) của position đổ về client
	@GetMapping("/")
	public String showApplication(Model model) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date datecurrent = new Date();
		ArrayList<TitlePosition> filterTitle = new ArrayList<TitlePosition>();
		ArrayList<TitlePosition> rawList = new ArrayList<TitlePosition>();
		FormApply formapply = new FormApply();
		rawList = applydao.getAllName();

		for (TitlePosition titlePosition : rawList) {
			try {
				if(datecurrent.before(dateFormat.parse(titlePosition.getExpDate()))) {
					filterTitle.add(titlePosition);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("formapply", formapply);
		model.addAttribute("titleList", filterTitle);
		return "homeApply";
	}

	//Lấy thông tin chi tiết của position
	@PostMapping("/getcontent")
	public ResponseEntity<?> getConten(@RequestBody TitlePosition data) {
		Position position = applydao.getOnePositionContent(data.getIdPos(),data.getName());
		return ResponseEntity.ok(position);
	}


	@PostMapping("/sendApply")
	public String getNewRecruitment(@ModelAttribute FormApply formapply,RedirectAttributes attr, Model model) {
		DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
		Date datecurrent = new Date();
		
		String idCan = dateformat.format(datecurrent);
		String nameCan =formapply.getF_name();
		String cmnd =formapply.getF_idcard();
		String email = formapply.getF_email();
		String phone = formapply.getF_phone();
		Boolean gender = formapply.getF_gender().equals("Male") ? true : false;
		String dob = formapply.getF_DOB() == null ? "not" : formapply.getF_DOB();
		String linkCV = "not";
		String namePos = formapply.getF_position();
		String dateImport = "not";
		String workExp = formapply.getF_workExp().equals("") ?  "not" : formapply.getF_workExp();
		
		// Upload file lên google
		MultipartFile filedata = formapply.getF_filedata();
		
		try {
			String id = GG.up(cmnd+"-"+idCan,filedata,0);
			linkCV ="https://drive.google.com/open?id="+id;
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String status ="New";
		Candidate candidate = new Candidate(idCan, nameCan, cmnd, email, phone, gender, dob, linkCV, namePos, dateImport,workExp,"not",null, status, null,null,null);
		applydao.addCandidate_S(candidate);

		attr.addAttribute("IDPOS",candidate.getNamePos());
		attr.addAttribute("IDCAN",candidate.getIdCan());
		return "redirect:/quiz";
		
//		HashSet<String> dsCMNDSys = new HashSet<>();
//		ArrayList<Candidate> dsCanSys = candidatedao.getCandidateByPosName(applydao.getPositionbyId(namePos).getName());
//		for(Candidate can : dsCanSys) {
//			dsCMNDSys.add(can.getCmnd());
//		}
//		if(dsCMNDSys.add(cmnd)) {
//			applydao.addCandidate_S(candidate);
//
//			attr.addAttribute("IDPOS",candidate.getNamePos());
//			attr.addAttribute("IDCAN",candidate.getIdCan());
//			return "redirect:/quiz";
//		}
//		else {
//			model.addAttribute("messa","NOT Ok");
//			return "redirect:/";
//		}
	}
}