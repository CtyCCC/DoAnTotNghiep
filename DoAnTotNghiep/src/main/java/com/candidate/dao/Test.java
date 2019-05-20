package com.candidate.dao;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.entity.Candidate;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CandidateDAO dao = new CandidateDAO();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("H:mm:ss");
//		Map<String, Integer> rate = new HashMap<String, Integer>();
//		rate.put("score", 10);
//		rate.put("time", 20);
//		Candidate can = new Candidate("can_15", "Manh Cuong", "025524128", "cuong@gmail.com", "0112121212", true, "11/11/1997", "ssss", "C#", "2/3/2019", rate, "new", null, null, null);
		//System.out.println(dao.getAllCandidate_M());
		//System.out.println(dao.getCandidateById("CAN1").getProbation());
//		dao.addCandidate(can, "Candidate_M");
//		dao.updateStatusCandidateById("can_2", "222222222", "Interview");
//		dao.deleteCandidate("CAN3", "333333333");
//		ArrayList<Object> logs = new ArrayList<>();
//		Map<String, Object> log1 = new HashMap<String, Object>();
//		log1.put("by", "admin");
//		log1.put("date", "14-05-2019");
//		log1.put("time", java.time.LocalTime.now().format(formatter2));
//		log1.put("method", "Test");
//		log1.put("change", "Luxubu");
//		logs.add(log1);
//		Map<String, Object> log2 = new HashMap<String, Object>();
//		log2.put("by", "HR");
//		log2.put("time", java.time.LocalDate.now().format(formatter));
//		log2.put("change", "Set Interview");
//		logs.add(0,log2);
//		dao.addLog("CAN1", "111111111", logs);
//		System.out.println(dao.getAllLog("CAN1"));
		
		ArrayList<Candidate> ds = dao.getImportCandidate_S("Product Manager", 10, 2);
		for(Candidate can : ds) {
			System.out.println(can);
		}
		
	}

}