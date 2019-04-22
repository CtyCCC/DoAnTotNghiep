package com.apply.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.apply.form.TitlePosition;
import com.entity.Candidate;
import com.entity.Position;

public class ApplyDao {
	
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();

        DynamoDB dynamoDB = new DynamoDB(client);
  
        //Lấy tên,id,.. của position 
        public ArrayList<TitlePosition> getAllName () {
    		ArrayList<TitlePosition> titleList = new ArrayList<TitlePosition>();
    		ScanResult rs = null;
    		do {
    			ScanRequest req = new ScanRequest();
    			req.withTableName("Position");
    			if(rs != null){
    	            req.setExclusiveStartKey(rs.getLastEvaluatedKey());
    	        }
    			rs = client.scan(req);
    			List<Map<String, AttributeValue>> rows = rs.getItems();
    			 
    	        for(Map<String, AttributeValue> map : rows){
    	            try{
    	                //AttributeValue v = map.get("id_can");
    	                String idPos = map.get("idPos").getS();
    	                String name = map.get("name").getS();
    	                String area= map.get("area").getS();
    	                String expDate= map.get("expDate").getS();
    	                TitlePosition pos = new TitlePosition(idPos, name,area,expDate);
    	                titleList.add(pos);
    	            } catch (NumberFormatException e){
    	                System.out.println(e.getMessage());
    	            }
    	        }
    			
    		}while(rs.getLastEvaluatedKey() != null);
    		return titleList;
    	}
        
        // Lấy toàn bộ thông tin của 1 position
        public Position getOnePositionContent(String idP,String nameP){
        	
        	Position position=null;
        	Table table =dynamoDB.getTable("Position");
        	GetItemSpec spec = new GetItemSpec().withPrimaryKey("idPos",idP,"name",nameP);
        	try {
        		Item outcome = table.getItem(spec);
        		position= new Position(outcome.get("idPos").toString()
        										,outcome.get("name").toString()
        										,outcome.get("area").toString()
        										,""
        										,outcome.get("requirement").toString()
        										,outcome.get("benefit").toString()
        										,outcome.get("description").toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
        	
        	return position;
        }
        
        //Hàm format string theo định dạng
        public Position formatContentPosition(Position position) {
				String re = position.getRequirement();
				position.setRequirement(formatContent(re));
				String be = position.getBenefit();
				position.setBenefit(formatContent(be));
				String des = position.getDescription();
				position.setDescription(formatContent(des));
				return position;
        }
        public String formatContent(String content) {
        	String[] ar = content.split("-");
        	String result = "";
        	for (int i = 0; i<ar.length;i++) {
        		result = result + "-" + ar[i] + "\n";
        	}
        	return result;
        }
        
        //Thêm 1 candidate mới vào Candidate_S
        public void addCandidate_S(Candidate can) {
    		Table table = dynamoDB.getTable("Candidate_S");
    		System.out.println(can);
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
    							.withString("dateImport", "not")
    							.withString("linkCV", can.getLinkCV())
    							.withString("namePos", can.getNamePos())
    							.withString("status", can.getStatus()));

    			System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

    		}
    		catch (Exception e) {
    			System.err.println(e.getMessage());
    		}
    	}
        
        public void updateRateofCadidate(Candidate candidate) {
        	Table table = dynamoDB.getTable("Candidate_S");
        	Map<String, Object> infoMap = new HashMap<String, Object>();
        	infoMap.put("score","123");
        	infoMap.put("time","10000");
        	infoMap.put("total","1234");
        	UpdateItemSpec updateitem = new UpdateItemSpec()
        								.withPrimaryKey("idCan",candidate.getIdCan(),"cmnd",candidate.getCmnd())
        								.withUpdateExpression("set rate = :val")
        								.withValueMap(new ValueMap()
        										.withMap(":val",infoMap))
        								.withReturnValues(ReturnValue.UPDATED_NEW);
        	UpdateItemOutcome outcome = table.updateItem(updateitem);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
        }
}