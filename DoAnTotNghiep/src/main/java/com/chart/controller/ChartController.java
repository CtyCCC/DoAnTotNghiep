package com.chart.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {

	@GetMapping("/chartjs")
	public String showChart(Model model) {
		
		return "chartjs";
	}
	
	public ResponseEntity<?> getChart(HttpServletRequest request){
		String key  		= request.getParameter("type");
		String time 		= request.getParameter("time");
		String[] daterange  = time.trim().split("-");
		String date1		= "";
		String date2		= "";
		if(daterange.length > 1) {
			date1 = daterange[0];
			date2 = daterange[1];
			key.concat("Range");
		}else {
			date1 = daterange[0];
		}
		switch (key) {
		case "Area":
			// area chart với ngày đã chọn
			break;
		case "Line":
			//Line chart với ngày đã chọn
			break;
		case "Dount":
			//Dount chart với ngày đã chọn
			break;
		case"Bar":
			//Bar chart với ngày đã chọn
			break;
		case "AreaRange":
			// Arean Range chart với ngày đã chọn
			break;
		case "LineRange":
			//Line Range chart với ngày đã chọn
			break;
		case "DountRange":
			//Dount Range chart với ngày đã chọn
			break;
		case"BarRange":
			//Bar Range chart với ngày đã chọn
			break;	
		}
		
		return ResponseEntity.ok("");
	}
}
