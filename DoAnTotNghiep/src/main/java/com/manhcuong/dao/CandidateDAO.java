package com.manhcuong.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.manhcuong.entity.Candidate;

@Repository
public class CandidateDAO {
	
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();

        DynamoDB dynamoDB = new DynamoDB(client);
	
	
	public ArrayList<Candidate> getAllCandidate_S () {
		ArrayList<Candidate> ds = new ArrayList<Candidate>();
		ScanResult rs = null;
		do {
			ScanRequest req = new ScanRequest();
			req.withTableName("Candidate_S");
			if(rs != null){
	            req.setExclusiveStartKey(rs.getLastEvaluatedKey());
	        }
			rs = client.scan(req);
			List<Map<String, AttributeValue>> rows = rs.getItems();
			 
	        for(Map<String, AttributeValue> map : rows){
	            try{
	                //AttributeValue v = map.get("id_can");
	                String id_can = map.get("id_can").getS();
	                String name_can = map.get("name").getS();
	                String cmnd = map.get("cmnd").getS();
	                String email = map.get("email").getS();
	                String phone = map.get("phone").getS();
	                boolean gender = map.get("gender").getBOOL();
	                String dob = map.get("dob").getS();
	                String linkCV = map.get("linkCV").getS();
	                String name_pos = map.get("position").getS();
	                String status = map.get("status").getS();
	                Map<String, AttributeValue> rate = (Map<String, AttributeValue>) map.get("rate").getM();
	                Candidate newCan_S = new Candidate(id_can, name_can, cmnd, email, phone, gender, dob, linkCV, name_pos, rate, status, null, null, null);
	                ds.add(newCan_S);
	                
	                //System.out.println(rs);
	           
	                //Lấy time trong Candidate
//	                int time = Integer.parseInt(newCan_S.getRate().get("time").getN());
//	                System.out.println(time);
      
	            } catch (NumberFormatException e){
	                System.out.println(e.getMessage());
	            }
	        }
			
		}while(rs.getLastEvaluatedKey() != null);
		return ds;
	}
}
