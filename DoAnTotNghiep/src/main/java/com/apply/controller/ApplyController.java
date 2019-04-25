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
	
	
	@PostMapping("/homeApply")
	public String getNewRecruitment(@ModelAttribute("formapply") @Valid FormApply formapply,BindingResult bind, RedirectAttributes attr ) {
		if (bind.hasErrors()) {
            return "homeApply";
        }
		else {
			DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
			Date datecurrent = new Date();
			Path path=null;
			MultipartFile filedata = formapply.getF_filedata();
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
			String nameCan =formapply.getF_name();
			String cmnd =formapply.getF_idcard();
			String email = formapply.getF_email();
			String phone = formapply.getF_phone();
			Boolean gender = formapply.getF_gender()=="Male" ? true : false;
			String dob = formapply.getF_DOB();
			String linkCV = path.toString();
			String namePos = formapply.getF_position();
			String dateImport = "";
			String workExp = formapply.getF_workExp();
			
			Map<String,Object> rate =null;
			String status ="New";
			Candidate candidate = new Candidate(idCan, nameCan, cmnd, email, phone, gender, dob, linkCV, namePos, dateImport,workExp,"", rate, status, null,null,null);
			applydao.addCandidate_S(candidate);
			
			attr.addAttribute("IDPOS",candidate.getNamePos());
			attr.addAttribute("IDCAN",candidate.getIdCan());
			return "redirect:/quiz";
		}
	}
}