package com.manhcuong.DynamoDb;

import java.util.Arrays;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class CreateTableCandidate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
	            .build();

	        DynamoDB dynamoDB = new DynamoDB(client);
	        
	        
	        //String tableName = "Candidate_M";
	        String tableName = "Candidate_S";
	        try {
	            System.out.println("Attempting to create table; please wait...");
	            Table table = dynamoDB.createTable(tableName,
	                Arrays.asList(new KeySchemaElement("id_can", KeyType.HASH), // Partition key
	                    new KeySchemaElement("cmnd", KeyType.RANGE)), // Sort key
	                Arrays.asList(new AttributeDefinition("id_can", ScalarAttributeType.S),
	                    new AttributeDefinition("cmnd", ScalarAttributeType.S)),
	                new ProvisionedThroughput(10L, 10L));
	            table.waitForActive();
	            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

	        }
	        catch (Exception e) {
	            System.err.println("Unable to create table: ");
	            System.err.println(e.getMessage());
	        }    
	}

}