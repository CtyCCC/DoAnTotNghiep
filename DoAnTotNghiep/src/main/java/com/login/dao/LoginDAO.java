package com.login.dao;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.entity.User;

public class LoginDAO {
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
			.build();

	DynamoDB dynamoDB = new DynamoDB(client);

	public User getInformationUser(String username,String pass){

		User user=null;
		Table table =dynamoDB.getTable("User");
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("idPos",username,"name",pass);
		try {
			Item outcome = table.getItem(spec);
			user =new User(outcome.getString("id_user"),
							outcome.getString("name_user"),
							outcome.getString("code"),
							outcome.getString("pass"),
							outcome.getString("tk"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		return user;
	}
}
