package com.candidate.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.entity.Candidate;

@Repository
public class CandidateDAO {

	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
			.build();

	DynamoDB dynamoDB = new DynamoDB(client);

	//Cách này củ chuối quá nên dùng cách dưới nhé :))
	//	public ArrayList<Candidate> getAllCandidate_M () {
	//		ArrayList<Candidate> ds = new ArrayList<Candidate>();
	//		ScanResult rs = null;
	//		do {
	//			ScanRequest req = new ScanRequest();
	//			req.withTableName("Candidate_M");
	//			if(rs != null){
	//	            req.setExclusiveStartKey(rs.getLastEvaluatedKey());
	//	        }
	//			rs = client.scan(req);
	//			List<Map<String, AttributeValue>> rows = rs.getItems();
	//			 
	//	        for(Map<String, AttributeValue> map : rows){
	//	            try{
	//	                //AttributeValue v = map.get("id_can");
	//	                String idCan = map.get("idCan").getS();
	//	                String nameCan = map.get("nameCan").getS();
	//	                String cmnd = map.get("cmnd").getS();
	//	                String email = map.get("email").getS();
	//	                String phone = map.get("phone").getS();
	//	                boolean gender = map.get("gender").getBOOL();
	//	                String dob = map.get("dob").getS();
	//	                String dateImport = map.get("dateImport").getS();
	//	                String linkCV = map.get("linkCV").getS();
	//	                String namePos = map.get("namePos").getS();
	//	                String status = map.get("status").getS();
	//	                Map<String, AttributeValue> rate = (Map<String, AttributeValue>) map.get("rate").getM();
	//	                Candidate newCan_S = new Candidate(idCan, nameCan, cmnd, email, phone, gender, dob, linkCV, namePos, dateImport, rate, status, null, null, null);
	//	                ds.add(newCan_S);
	//	                
	//	                //System.out.println(rs);
	//	           
	//	                //Lấy time trong Candidate
	////	                int time = Integer.parseInt(newCan_S.getRate().get("time").getN());
	////	                System.out.println(time);
	//      
	//	            } catch (NumberFormatException e){
	//	                System.out.println(e.getMessage());
	//	            }
	//	        }
	//			
	//		}while(rs.getLastEvaluatedKey() != null);
	//		return ds;
	//	}

	//Cách này xịn vkl lun nè :))
	//Search all Candidate
	public ArrayList<Candidate> getAllCandidate_M(){
		ArrayList<Candidate> ds = new ArrayList<Candidate>();
		Table table = dynamoDB.getTable("Candidate_M");
		ScanSpec scanSpec = new ScanSpec();

		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);

			Iterator<Item> iter = items.iterator();
			while (iter.hasNext()) {
				Item item = iter.next();
				Candidate c = new Candidate(item.getString("idCan"), 
						item.getString("nameCan"), 
						item.getString("cmnd"), 
						item.getString("email"), 
						item.getString("phone"), 
						item.getBoolean("gender"), 
						item.getString("dob"), 
						item.getString("linkCV"), 
						item.getString("namePos"),
						item.getString("dateImport"),
						item.getMap("rate"), 
						item.getString("status"), 
						null,null,null);
				ds.add(c);
			}

		}
		catch (Exception e) {
			System.err.println("Unable to scan the table:");
			System.err.println(e.getMessage());
		}

		return ds;
	}

	//Search Candidate by id
	public Candidate getCandidateById(String id) {
		Table table = dynamoDB.getTable("Candidate_M");
		ScanSpec scanSpec = new ScanSpec()
				.withFilterExpression("#ii = :iiii")
				.withNameMap(new NameMap().with("#ii", "idCan"))
				.withValueMap(new ValueMap().withString(":iiii", id));
		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);
			Iterator<Item> iter = items.iterator();
			Candidate can = new Candidate();
			while (iter.hasNext()) {
				Item item = iter.next();
				can = new Candidate(item.getString("idCan"), 
						item.getString("nameCan"), 
						item.getString("cmnd"), 
						item.getString("email"), 
						item.getString("phone"), 
						item.getBoolean("gender"), 
						item.getString("dob"), 
						item.getString("linkCV"), 
						item.getString("namePos"),
						item.getString("dateImport"),
						item.getMap("rate"), 
						item.getString("status"), 
						item.getMap("interview")
						,null,null);
			}
			return can;
		}
		catch (Exception e) {
			System.err.println("Unable to scan the table:");
			System.err.println(e.getMessage());
		}
		return null;
	}

	//Add new Candidate
	public void addCandidate(Candidate can, String tableName) {
		Table table = dynamoDB.getTable(tableName);
		try {
			System.out.println("Adding a new item...");
			PutItemOutcome outcome = table
					.putItem(new Item()
							.withPrimaryKey("idCan", can.getIdCan(), "cmnd", can.getCmnd())
							.withString("nameCan", can.getNameCan())
							.withString("email", can.getEmail())
							.withString("phone", can.getPhone())
							.withBoolean("gender", can.isGender())
							.withString("dob", can.getDob())
							.withString("dateImport", can.getDateImport())
							.withString("linkCV", can.getLinkCV())
							.withString("namePos", can.getNamePos())
							.withString("status", can.getStatus())
							.withMap("rate", can.getRate()));

			System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	//Update Status
	public void updateStatusCandidateById(String id,String cmnd, String stus) {
		Table table = dynamoDB.getTable("Candidate_M");
		UpdateItemSpec updateItemSpec = new UpdateItemSpec();
		//Status = Interview
		if(stus.equals("Interview")) {
			updateItemSpec = new UpdateItemSpec()
					.withPrimaryKey("idCan", id, "cmnd", cmnd)
					.withUpdateExpression("set #ss = :s")
					.withNameMap(new NameMap().with("#ss", "status"))
					.withValueMap(new ValueMap().withString(":s", stus))
					.withReturnValues(ReturnValue.UPDATED_NEW);
			
			
			//Chưa tìm ra cách thêm Attribute(type=Map<>)
//			Map<String, String> expressionAttributeNames = new HashMap<String, String>();
//			expressionAttributeNames.put("#stus", "status");
//			expressionAttributeNames.put("#I", "interview");
//			
//			Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
//			expressionAttributeValues.put(":val1", new HashSet<String>(Arrays.asList("Author YY","Author ZZ")));
//			expressionAttributeValues.put(":val2", stus);
//			
//			UpdateItemOutcome outcome =  table.updateItem(
//				    "idCan",id,          // idCan
//				    "cmnd",cmnd,           // cmnd
//				    "add #I :val1 "
//				    + "set #stus = :val2", // UpdateExpression
//				    expressionAttributeNames,
//				    expressionAttributeValues);
		
		
		}else if(stus.equals("Offer")) {
			//Thông tin Offer
			Map<String, Object> ofr = new HashMap<String, Object>(); 
			ofr.put("curSal","500$");
			ofr.put("expectSal","800$");
			ofr.put("ofrSal","650$");
			ofr.put("result","Pass");
			
			updateItemSpec = new UpdateItemSpec()
					.withPrimaryKey("idCan", id, "cmnd", cmnd)
					.withUpdateExpression("set #ss = :s, offer = :o") 
					.withNameMap(new NameMap().with("#ss", "status"))
					.withValueMap(new ValueMap().withString(":s", stus)
							.withMap(":o", ofr))
					.withReturnValues(ReturnValue.UPDATED_NEW);
		
		}else if(stus.equals("Probation")) {
			//Thông tin probation
			Map<String, Object> pro = new HashMap<String, Object>(); 
			pro.put("from","20/2/2019");
			pro.put("to","25/5/2019");
			pro.put("note","Good");
			pro.put("result","Pass");
			
			updateItemSpec = new UpdateItemSpec()
					.withPrimaryKey("idCan", id, "cmnd", cmnd)
					.withUpdateExpression("set #ss = :s, probation = :p") 
					.withNameMap(new NameMap().with("#ss", "status"))
					.withValueMap(new ValueMap().withString(":s", stus)
							.withMap(":p", pro))
					.withReturnValues(ReturnValue.UPDATED_NEW);
		}
		
		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		
	}
	
	public void addInterview(String id, String cmnd, ArrayList<Object> rounds) {
		Table table = dynamoDB.getTable("Candidate_M");
		UpdateItemSpec updateItemSpec = new UpdateItemSpec();
		//Thông tin round
//		Map<String, Object> rnd = new HashMap<String, Object>();
//		rnd.put("idRound",round.get("idRound"));
//		rnd.put("interviewer",round.get("interviewer"));
//		rnd.put("date", round.get("date"));
//		rnd.put("time", round.get("time"));
//		rnd.put("venue", round.get("venue"));
//		rnd.put("note", round.get("note"));
//		rnd.put("result", "Unknown");
		
		//Danh sách round
//		ArrayList<Object> rounds = new ArrayList<>();
//		rounds.add(rnd);
		
		//Thông tin interview
		Map<String, Object> inter = new HashMap<String, Object>(); 
		inter.put("finalResult","Unknown");
		inter.put("rounds", rounds);
		
		updateItemSpec = new UpdateItemSpec()
				.withPrimaryKey("idCan", id, "cmnd", cmnd)
				.withUpdateExpression("set #ss = :s, interview = :i")
				.withNameMap(new NameMap().with("#ss", "status"))
				.withValueMap(new ValueMap().withString(":s", "Wait for interview")
						.withMap(":i", inter))
				.withReturnValues(ReturnValue.UPDATED_NEW);
		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
