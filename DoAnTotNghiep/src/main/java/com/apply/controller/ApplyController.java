package com.apply.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.apply.dao.ApplyDao;
import com.apply.form.TitlePosition;
import com.position.entity.Position;

@Controller

public class ApplyController {
	ApplyDao applydao = new ApplyDao();
	@GetMapping("/home.clt")
	public String showApplytition(Model model) {
		
		ArrayList<TitlePosition> titleList = new ArrayList<TitlePosition>();
		titleList = applydao.getAllName();
		
		model.addAttribute("titleList", titleList);
		return "homeApply";
	}
	
	@PostMapping("/getcontent")
	public ResponseEntity<?> getConten(@RequestBody TitlePosition data) {
		Position position = applydao.getOnePositionContent(data.getIdPos(),data.getName());
		Position pos2 = applydao.formatAllResult(position);
		return ResponseEntity.ok(pos2);
	}
	
}