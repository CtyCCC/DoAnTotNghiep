package com.position.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.entity.Position;

public class PositionDAO {
	
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();

        DynamoDB dynamoDB = new DynamoDB(client);
        

        public ArrayList<Position> getAllPosition () {
    		ArrayList<Position> ds = new ArrayList<Position>();
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
    	                String requirement= map.get("requirement").getS();
    	                String benefit= map.get("benefit").getS();
    	                String description= map.get("description").getS();
    	                Position pos = new Position(idPos, name, area, expDate, requirement, benefit, description);
    	                ds.add(pos);
    	            } catch (NumberFormatException e){
    	                System.out.println(e.getMessage());
    	            }
    	        }
    			
    		}while(rs.getLastEvaluatedKey() != null);
    		return ds;
    	}
        
        //lấy position theo name, ko tốn công query trong db lại vì sài thằng arr của thằng getAll
        public Position getOnePositionByName (String name, ArrayList<Position> arr) {
        	for (Position position : arr) {
        		if (position.getName().equals(name))
        			return position;
			}
        	return null;
        }
        
        //Get name của array position đổ vào select box
        
        // đưa vào mảng Position, định dạng lại 3 thằng Requirement, benefit, description
        // của toàn bộ Position trong mảng
        // thành kiểu như hàm dưới, in cho đẹp
        public void formatAllResult(ArrayList<Position> arr) {
        	for (Position position : arr) {
				String re = position.getRequirement();
				position.setRequirement(formatContent(re));
				String be = position.getBenefit();
				position.setBenefit(formatContent(be));
				String des = position.getDescription();
				position.setDescription(formatContent(des));
			}
        }
        
        //xử "Chien-Pro-Haha" thành 
        //"- Chien
        // - Pro
        // - Haha "
        public String formatContent(String content) {
        	String[] ar = content.split("-");
        	String result = "";
        	for (int i = 0; i<ar.length;i++) {
        		result = result + "-" + ar[i] + "\n";
        	}
        	return result;
        }

}
