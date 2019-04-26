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
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
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
						item.getString("workExp"),
						item.getString("avatar"),
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
						item.getString("workExp"),
						item.getString("avatar"),
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
	
	//Delete Candidate
	public void deleteCandidate(String id, String cmnd) {
		Table table = dynamoDB.getTable("Candidate_M");
		DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
	            .withPrimaryKey(new PrimaryKey("idCan", id, "cmnd", cmnd));

	        // Conditional delete (we expect this to fail)

	        try {
	            System.out.println("Attempting a conditional delete...");
	            table.deleteItem(deleteItemSpec);
	            System.out.println("DeleteItem succeeded");
	        }
	        catch (Exception e) {
	            System.err.println(e.getMessage());
	        }
	}

	//Update Status
	public void updateStatusCandidateById(String id,String cmnd, String stus) {
		Table table = dynamoDB.getTable("Candidate_M");
		UpdateItemSpec updateItemSpec = new UpdateItemSpec()
					.withPrimaryKey("idCan", id, "cmnd", cmnd)
					.withUpdateExpression("set #ss = :s")
					.withNameMap(new NameMap().with("#ss", "status"))
					.withValueMap(new ValueMap().withString(":s", stus))
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
	
	//Add Interview
	public void addInterview(String id, String cmnd, ArrayList<Object> rounds, String stus) {
		Table table = dynamoDB.getTable("Candidate_M");
		UpdateItemSpec updateItemSpec = new UpdateItemSpec();
		
		//Thông tin interview
		Map<String, Object> inter = new HashMap<String, Object>(); 
		inter.put("finalResult","Unknown");
		inter.put("rounds", rounds);
		
		updateItemSpec = new UpdateItemSpec()
				.withPrimaryKey("idCan", id, "cmnd", cmnd)
				.withUpdateExpression("set #ss = :s, interview = :i")
				.withNameMap(new NameMap().with("#ss", "status"))
				.withValueMap(new ValueMap().withString(":s", stus)
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
	
	//Edit thông tin candidate
	public void editProfile(Candidate can) {
		Table table = dynamoDB.getTable("Candidate_M");
		UpdateItemSpec updateItemSpec = new UpdateItemSpec()
					.withPrimaryKey("idCan", can.getIdCan(), "cmnd", can.getCmnd())
					.withUpdateExpression("set nameCan = :na, "
							+ "email = :em, "
							+ "phone = :ph, "
							+ "gender = :ge, "
							+ "dob = :do, "
							+ "namePos = :np, "
							+ "dateImport = :da, "
							+ "workExp = :wo, "
							+ "avatar = :av, "
							+ "rate.score = :sc, #t = :ti, #tt = :to")
					.withNameMap(new NameMap().with("#t", "rate.time").with("#tt", "rate.total"))
					.withValueMap(new ValueMap()
							.withString(":na", can.getNameCan())
							.withString(":em", can.getEmail())
							.withString(":ph", can.getPhone())
							.withBoolean(":ge", can.isGender())
							.withString(":do", can.getDob())
							.withString(":np", can.getNamePos())
							.withString(":da", can.getDateImport())
							.withString(":wo", can.getWorkExp())
							.withString(":av", can.getAvatar())
							.withInt(":sc", (int) can.getRate().get("score"))
							.withInt(":ti", (int) can.getRate().get("time"))
							.withInt(":to", (int) can.getRate().get("total")))
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
	
	//Lấy round bằng idRound
	public Map<String, Object> getRoundById(String idRound, String idCan){
		ArrayList<Object> arr = new ArrayList<>();
		Map<String, Object> r = null;
		Table table = dynamoDB.getTable("Candidate_M");
		ScanSpec scanSpec = new ScanSpec()
				.withFilterExpression("#ii = :iiii")
				.withNameMap(new NameMap().with("#ii", "idCan"))
				.withValueMap(new ValueMap().withString(":iiii", idCan));
		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);
			Iterator<Item> iter = items.iterator();
			while (iter.hasNext()) {
				Item item = iter.next();
				arr = (ArrayList<Object>) item.getMap("interview").get("rounds");
			}
			for(int i = 0; i<arr.size();i++) {
				Map<String, Object> a = (Map<String, Object>) arr.get(i);
				if(a.get("idRound").equals(idRound)) {
					r = a;
				}
			}
			return r;
		}
		catch (Exception e) {
			System.err.println("Unable to scan the table:");
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	//Edit thông tin round bằng id
	public void editRoundById(ArrayList<Object> dsRound, String idCan, String cmnd) {
		Table table = dynamoDB.getTable("Candidate_M");
		UpdateItemSpec updateItemSpec = new UpdateItemSpec()
					.withPrimaryKey("idCan", idCan, "cmnd", cmnd)
					.withUpdateExpression("set interview.rounds = :ro")
					.withValueMap(new ValueMap()
							.withList(":ro", dsRound))
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
