package com.login.dao;

import java.util.Iterator;

import org.springframework.stereotype.Repository;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.entity.Candidate;
import com.entity.User;

@Repository
public class LoginDAO {
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
			.build();

	DynamoDB dynamoDB = new DynamoDB(client);

	public User getInformationUser(String username) {
		User user =null;
		Table table = dynamoDB.getTable("User");
		ScanSpec scanSpec = new ScanSpec()
				.withFilterExpression("#ii = :iiii")
				.withNameMap(new NameMap().with("#ii", "tk"))
				.withValueMap(new ValueMap().withString(":iiii", username));
		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);
			Iterator<Item> iter = items.iterator();
			while (iter.hasNext()) {
				Item item = iter.next();
				user =new User(item.getString("id_user"),
							   item.getString("tk"),
						       item.getString("code"),
						       item.getString("name_user"),
						       item.getString("pass"),
						       item.getString("avatar"));
			}
			return user;
		}
		catch (Exception e) {
			System.err.println("Unable to scan the table:");
			System.err.println(e.getMessage());
		}
		return null;
	}

}
