package com.candidate.dao;

import java.util.HashMap;
import java.util.Map;

import com.candidate.entity.Candidate;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CandidateDAO dao = new CandidateDAO();
//		Map<String, Integer> rate = new HashMap<String, Integer>();
//		rate.put("score", 10);
//		rate.put("time", 20);
//		Candidate can = new Candidate("can_15", "Manh Cuong", "025524128", "cuong@gmail.com", "0112121212", true, "11/11/1997", "ssss", "C#", "2/3/2019", rate, "new", null, null, null);
		//System.out.println(dao.getAllCandidate_M());
		//System.out.println(dao.getCandidateById("can_1"));
//		dao.addCandidate(can, "Candidate_M");
		dao.updateStatusCandidateById("can_2", "222222222", "Probation");
	}

}