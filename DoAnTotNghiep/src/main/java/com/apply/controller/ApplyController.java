package com.apply.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.apply.dao.ApplyDao;
import com.apply.form.TitlePosition;
import com.entity.Position;

@Controller

public class ApplyController {
	ApplyDao applydao = new ApplyDao();
	@GetMapping("/homeApply")
	public String showApplytition(Model model) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date datecurrent = new Date();
		ArrayList<TitlePosition> filterTitle = new ArrayList<TitlePosition>();
		ArrayList<TitlePosition> rawList = new ArrayList<TitlePosition>();
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
		System.out.println();
		model.addAttribute("titleList", filterTitle);
		return "homeApply";
	}
	
	@PostMapping("/getcontent")
	public ResponseEntity<?> getConten(@RequestBody TitlePosition data) {
		Position position = applydao.getOnePositionContent(data.getIdPos(),data.getName());
		Position pos2 = applydao.formatContentPosition(position);
		return ResponseEntity.ok(pos2);
	}
	
}