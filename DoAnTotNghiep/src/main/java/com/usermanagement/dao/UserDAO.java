package com.usermanagement.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.entity.Position;
import com.entity.Questions;
import com.entity.User;

@Repository
public class UserDAO {
	
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
			.build();

	DynamoDB dynamoDB = new DynamoDB(client);


	public ArrayList<User> getAllUser () {
		ArrayList<User> ds = new ArrayList<User>();
		ScanResult rs = null;
		do {
			ScanRequest req = new ScanRequest();
			req.withTableName("User");
			if(rs != null){
				req.setExclusiveStartKey(rs.getLastEvaluatedKey());
			}
			rs = client.scan(req);
			List<Map<String, AttributeValue>> rows = rs.getItems();

			for(Map<String, AttributeValue> map : rows){
				try{
					String id_user = map.get("id_user").getS();
					String tk = map.get("tk").getS();
					String code= map.get("code").getS();
					String name= map.get("name_user").getS();
					String pass= map.get("pass").getS();
					String avatar= map.get("avatar").getS();
					User u = new User(id_user, tk, code, name, pass, avatar);
					ds.add(u);
				} catch (NumberFormatException e){
					System.out.println(e.getMessage());
				}
			}

		}while(rs.getLastEvaluatedKey() != null);
		return ds;
	}
	
	public void addNewUser(User q) {

		Table table = dynamoDB.getTable("User");

		try {
			System.out.println("Adding a new item...");
			table.putItem(new Item().withPrimaryKey("id_user", q.getId_user(), "tk", q.getTk())
							.withString("name_user",q.getName())
	                		.withString("pass",q.getPass())
	                		.withString("code",q.getCode())
	                		.withString("avatar",q.getAvatar()));

			System.out.println("PutItem succeeded:" + q);

		}
		catch (Exception e) {
			System.err.println("Unable to add item: " + q.getName());
			System.err.println(e.getMessage());
		}

	}
	
	public void updateUser(String id, String tk, String name, String code, String avatar) {

		Table table = dynamoDB.getTable("User");

		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("id_user", id, "tk", tk)
				.withUpdateExpression("set name_user = :a, code = :b, avatar = :c")
				.withValueMap(new ValueMap().withString(":a", name)
						.withString(":b", code)
						.withString(":c", avatar))
				.withReturnValues(ReturnValue.UPDATED_NEW);

		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

		}
		catch (Exception e) {
			System.err.println("Unable to update item: " + tk);
			System.err.println(e.getMessage());
		}
	}
	
	public void deleteUser(String id_user, String tk) {
		Table table = dynamoDB.getTable("User");
		DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
	            .withPrimaryKey(new PrimaryKey("id_user", id_user, "tk", tk));

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
	
	//tự tăng id Pos
		public String autoId(ArrayList<User> arr) {
			int count = arr.size()+1;
			return "user_"+count+"";
		}

}
