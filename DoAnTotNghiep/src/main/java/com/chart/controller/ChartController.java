package com.chart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.mortbay.util.ajax.JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chart.form.InfoChart;

@Controller
public class ChartController {

	@GetMapping("/chartjs")
	public String showChart(Model model) {
	
		return "chartjs";
	}
	
	@PostMapping("/chartjs")
	public @ResponseBody String getChart(HttpServletRequest req){
		String type  		=  req.getParameter("TypeChart");
		String time 		=  req.getParameter("RangeTime");
		String[] daterange  = time.split("-");
		JSONArray rawstatus = new JSONArray(req.getParameter("NameStatusData"));
		JSONArray rawposition = new JSONArray(req.getParameter("NamePositionData"));
		List<String> liststatus = new ArrayList<>();
		List<String> listposition = new ArrayList<>();
		String date1		= "";
		String date2		= "";
		if(daterange.length > 1) {
			date1 = daterange[0].trim();
			date2 = daterange[1].trim();
			type.concat("Range");
		}else {
			date1 = daterange[0].trim();
		}
		for(int i =0;i< rawposition.length();i++) {
			listposition.add(rawposition.getString(i));
		}
		for(int i =0;i< rawstatus.length();i++) {
			liststatus.add(rawstatus.getString(i));
		}
		System.out.println(type);
		System.out.println(time);
		System.out.println(date1);
		System.out.println(date2);
		System.out.println(liststatus);
		System.out.println(listposition);
		//System.out.println(ListStatus);
		switch (type) {
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
		
		return type;
	}
}
