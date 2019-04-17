package com.position.dynamodb;

import java.io.File;
import java.util.Iterator;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LoadDataPosition {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
	            .build();

	        DynamoDB dynamoDB = new DynamoDB(client);

	        Table table = dynamoDB.getTable("Position");

	        JsonParser parser = new JsonFactory().createParser(new File("Position.json"));

	        JsonNode rootNode = new ObjectMapper().readTree(parser);
	        Iterator<JsonNode> iter = rootNode.iterator();

	        ObjectNode currentNode;

	        while (iter.hasNext()) {
	            currentNode = (ObjectNode) iter.next();

	            String idPos = currentNode.path("idPos").asText();
	            String name = currentNode.path("name").asText();
	            String area = currentNode.path("area").asText();
	            String expDate = currentNode.path("expDate").asText();
	            String requirement = currentNode.path("requirement").asText();
	            String benefit = currentNode.path("benefit").asText();
	            String description = currentNode.path("description").asText();
	            try {
	                table.putItem(new Item().withPrimaryKey("idPos", idPos, "name", name)
	                		.withString("area",area)
	                		.withString("expDate",expDate)
	                		.withString("requirement",requirement)
	                		.withString("benefit",benefit)
	                		.withString("description",description)
	                		);
	                System.out.println("PutItem succeeded!");

	            }
	            catch (Exception e) {
	                System.err.println("Unable to add item!");
	                System.err.println(e.getMessage());
	                break;
	            }
	        }
	        parser.close();
	}
}
