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

public class LoadDataQuestion {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
	            .build();

	        DynamoDB dynamoDB = new DynamoDB(client);

	        Table table = dynamoDB.getTable("Question");

	        JsonParser parser = new JsonFactory().createParser(new File("questiondata.json"));

	        JsonNode rootNode = new ObjectMapper().readTree(parser);
	        Iterator<JsonNode> iter = rootNode.iterator();

	        ObjectNode currentNode;

	        while (iter.hasNext()) {
	            currentNode = (ObjectNode) iter.next();

	            String id_ques = currentNode.path("id_ques").asText();
	            String ques_content = currentNode.path("ques_content").asText();

	            try {
	                table.putItem(new Item().withPrimaryKey("id_ques", id_ques, "ques_content", ques_content)
	                		.withJSON("answer",currentNode.path("answer").toString())
	                		.withJSON("result",currentNode.path("result").toString())
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
