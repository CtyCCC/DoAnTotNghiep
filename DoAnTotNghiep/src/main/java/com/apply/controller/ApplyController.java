package com.apply.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apply.dao.ApplyDao;
import com.apply.form.FormApply;
import com.apply.form.TitlePosition;
import com.candidate.dao.CandidateDAO;
import com.entity.Candidate;
import com.entity.Interview;
import com.entity.Offer;
import com.entity.Position;
import com.entity.Probation;

@Controller

public class ApplyController {
	ApplyDao applydao = new ApplyDao();
	CandidateDAO candidatedao = new CandidateDAO();
	private static String UPLOADED_FOLDER = "C:/CV";
	
	// Lấy thông tin tổng quát(id,tên,ngày) của position đổ về client
	@GetMapping("/homeApply")
	public String showApplytition(Model model) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
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
		Position pos2 = applydao.formatContentPosition(position);
		return ResponseEntity.ok(pos2);
	}
	
	@PostMapping("/sendapply")
	public String getNewRecruitment(@ModelAttribute("formapply") FormApply form, RedirectAttributes attr) {
		DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date datecurrent = new Date();
		Path path=null;
		MultipartFile filedata = form.getF_filedata();
		byte[] bytes;
		
		try {
			bytes = filedata.getBytes();
			path = Paths.get(UPLOADED_FOLDER,filedata.getOriginalFilename());
			Files.write(path, bytes);
			System.out.println(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String idCan = dateformat.format(datecurrent);
		String nameCan = form.getF_firstname() +" "+ form.getF_lastname();
		String cmnd =form.getF_idcard();
		String email = form.getF_email();
		String phone = form.getF_phone();
		Boolean gender = form.getF_gender()=="Male" ? true : false;
		String dob = form.getF_DOB();
		String linkCV = path.toString();
		String namePos = form.getF_position();
		String dateImport = "";
		Map<String,Object> rate =null;
		String status ="New";
		Map<String,Object> interview =null;
		Map<String,Object> probation = null;
		Map<String,Object> offer = null;
		Candidate candidate = new Candidate(idCan, nameCan, cmnd, email, phone, gender, dob, linkCV, namePos, dateImport, rate, status, interview, offer, probation);
		applydao.addCandidate_S(candidate);
		
		attr.addAttribute("IDPOS",candidate.getNamePos());
		attr.addAttribute("IDCAN",candidate.getIdCan());
		return "redirect:/quiz";
	}
	
}