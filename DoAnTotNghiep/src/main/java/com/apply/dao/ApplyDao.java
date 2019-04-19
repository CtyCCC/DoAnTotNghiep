package com.apply.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.apply.form.TitlePosition;
import com.position.entity.Position;

public class ApplyDao {
	
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();

        DynamoDB dynamoDB = new DynamoDB(client);
  
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
        public Position formatAllResult(Position position) {
        	
        		
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
}