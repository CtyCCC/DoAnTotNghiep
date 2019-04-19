package com.manhcuong.DynamoDb;

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

public class LoadDataCandidate {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
	            .build();

	        DynamoDB dynamoDB = new DynamoDB(client);

	        //Table table = dynamoDB.getTable("Candidate_M");
	        Table table = dynamoDB.getTable("Candidate_S");

	        JsonParser parser = new JsonFactory().createParser(new File("candidatedata.json"));

	        JsonNode rootNode = new ObjectMapper().readTree(parser);
	        Iterator<JsonNode> iter = rootNode.iterator();

	        ObjectNode currentNode;

	        while (iter.hasNext()) {
	            currentNode = (ObjectNode) iter.next();

	            String id_can = currentNode.path("id_can").asText();
	            String cmnd = currentNode.path("cmnd").asText();

	            try {
	                table.putItem(new Item().withPrimaryKey("id_can", id_can, "cmnd", cmnd)
	                		.withJSON("name",currentNode.path("name").toString())
	                		.withJSON("email",currentNode.path("email").toString())
	                		.withJSON("phone",currentNode.path("phone").toString())
	                		.withJSON("gender",currentNode.path("gender").toString())
	                		.withJSON("dob",currentNode.path("dob").toString())
	                		.withJSON("linkCV",currentNode.path("linkCV").toString())
	                		.withJSON("rate",currentNode.path("rate").toString())
	                		.withJSON("position",currentNode.path("position").toString())
	                		.withJSON("status",currentNode.path("status").toString())
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
