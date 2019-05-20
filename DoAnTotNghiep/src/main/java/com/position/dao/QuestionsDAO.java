package com.position.dao;

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
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.entity.Questions;

@Repository
public class QuestionsDAO {

	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
			.build();

	DynamoDB dynamoDB = new DynamoDB(client);


	public ArrayList<Questions> getAllQuestions () {
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
	
	//tự tăng id ques
	public String autoIdQues(ArrayList<Questions> arr) {
		int count = arr.size()+1;
		return "QUES"+count+"";
	}

	public void addNewQues(Questions q) {

		Table table = dynamoDB.getTable("Questions");

		try {
			System.out.println("Adding a new item...");
			table.putItem(new Item().withPrimaryKey("idQues", q.getIdQues(), "content", q.getContent())
							.withString("a",q.getA())
	                		.withString("b",q.getB())
	                		.withString("c",q.getC())
	                		.withString("d",q.getD())
	                		.withString("answer",q.getAnswer()));

			System.out.println("PutItem succeeded:" + q);

		}
		catch (Exception e) {
			System.err.println("Unable to add item: " + q.getIdQues());
			System.err.println(e.getMessage());
		}

	}
	public void deleteQues(String id, String con) {
		Table table = dynamoDB.getTable("Questions");
		DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
	            .withPrimaryKey(new PrimaryKey("idQues", id, "content", con));

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


}
