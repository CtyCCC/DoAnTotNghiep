package com.quiz.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.entity.Questions;

@Repository
public class QuizDao {
	
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();

        DynamoDB dynamoDB = new DynamoDB(client);
        

        public ArrayList<Questions> getAllQuestionsQuiz () {
    		ArrayList<Questions> ds = new ArrayList<Questions>();
    		ScanResult rs = null;
    		do {
    			ScanRequest req = new ScanRequest();
    			req.withTableName("Questions");
    			if(rs != null){
    	            req.setExclusiveStartKey(rs.getLastEvaluatedKey());
    	        }
    			rs = client.scan(req);
    			List<Map<String, AttributeValue>> rows = rs.getItems();
    			 
    	        for(Map<String, AttributeValue> map : rows){
    	            try{
    	                //AttributeValue v = map.get("id_can");
    	                String idQues = map.get("idQues").getS();
    	                String content = map.get("content").getS();
    	                String a= map.get("a").getS();
    	                String b= map.get("b").getS();
    	                String c= map.get("c").getS();
    	                String d= map.get("d").getS();
    	                String answer= map.get("answer").getS();
    	                Questions qs = new Questions(idQues, content, a, b, c, d, answer);
    	                ds.add(qs);
    	            } catch (NumberFormatException e){
    	                System.out.println(e.getMessage());
    	            }
    	        }
    			
    		}while(rs.getLastEvaluatedKey() != null);
    		return ds;
    	}
}