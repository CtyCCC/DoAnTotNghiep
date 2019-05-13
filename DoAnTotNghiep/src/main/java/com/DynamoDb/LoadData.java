package com.DynamoDb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LoadData {
	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
			.build();

	static DynamoDB dynamoDB = new DynamoDB(client);
	public static void main(String[] args) throws JsonParseException, IOException {
		// TODO Auto-generated method stub

		loadCandidate();
//		loadPosition();
//		loadQuestion();
//		loadUser();

	}

	public static void loadCandidate() throws JsonParseException, IOException {
		Table table = dynamoDB.getTable("Candidate_M");
		//Table table = dynamoDB.getTable("Candidate_S");

		JsonParser parser = new JsonFactory().createParser(new File("candidatedata.json"));

		JsonNode rootNode = new ObjectMapper().readTree(parser);
		Iterator<JsonNode> iter = rootNode.iterator();

		ObjectNode currentNode;

		while (iter.hasNext()) {
			currentNode = (ObjectNode) iter.next();

			String id_can = currentNode.path("idCan").asText();
			String cmnd = currentNode.path("cmnd").asText();

			String interview = currentNode.path("interview").toString();
			String offer = currentNode.path("offer").toString();
			String probation = currentNode.path("probation").toString();
			try {
				if(!(probation.isEmpty())) {
					table.putItem(new Item().withPrimaryKey("idCan", id_can, "cmnd", cmnd)
							.withJSON("nameCan",currentNode.path("nameCan").toString())
							.withJSON("email",currentNode.path("email").toString())
							.withJSON("phone",currentNode.path("phone").toString())
							.withJSON("gender",currentNode.path("gender").toString())
							.withJSON("dob",currentNode.path("dob").toString())
							.withJSON("dateImport",currentNode.path("dateImport").toString())
							.withJSON("linkCV",currentNode.path("linkCV").toString())
							.withJSON("rate",currentNode.path("rate").toString())
							.withJSON("namePos",currentNode.path("namePos").toString())
							.withJSON("workExp",currentNode.path("workExp").toString())
							.withJSON("avatar",currentNode.path("avatar").toString())
							.withJSON("status",currentNode.path("status").toString())
							.withJSON("interview",currentNode.path("interview").toString())
							.withJSON("offer",currentNode.path("offer").toString())
							.withJSON("probation",currentNode.path("probation").toString())
							.withJSON("logs",currentNode.path("logs").toString())
							);
				}else if(!(offer.isEmpty())) {
					table.putItem(new Item().withPrimaryKey("idCan", id_can, "cmnd", cmnd)
							.withJSON("nameCan",currentNode.path("nameCan").toString())
							.withJSON("email",currentNode.path("email").toString())
							.withJSON("phone",currentNode.path("phone").toString())
							.withJSON("gender",currentNode.path("gender").toString())
							.withJSON("dob",currentNode.path("dob").toString())
							.withJSON("dateImport",currentNode.path("dateImport").toString())
							.withJSON("linkCV",currentNode.path("linkCV").toString())
							.withJSON("rate",currentNode.path("rate").toString())
							.withJSON("namePos",currentNode.path("namePos").toString())
							.withJSON("workExp",currentNode.path("workExp").toString())
							.withJSON("avatar",currentNode.path("avatar").toString())
							.withJSON("status",currentNode.path("status").toString())
							.withJSON("interview",currentNode.path("interview").toString())
							.withJSON("offer",currentNode.path("offer").toString())
							.withJSON("logs",currentNode.path("logs").toString())
							);
				}else if (!(interview.isEmpty())) {
					table.putItem(new Item().withPrimaryKey("idCan", id_can, "cmnd", cmnd)
							.withJSON("nameCan",currentNode.path("nameCan").toString())
							.withJSON("email",currentNode.path("email").toString())
							.withJSON("phone",currentNode.path("phone").toString())
							.withJSON("gender",currentNode.path("gender").toString())
							.withJSON("dob",currentNode.path("dob").toString())
							.withJSON("dateImport",currentNode.path("dateImport").toString())
							.withJSON("linkCV",currentNode.path("linkCV").toString())
							.withJSON("rate",currentNode.path("rate").toString())
							.withJSON("namePos",currentNode.path("namePos").toString())
							.withJSON("workExp",currentNode.path("workExp").toString())
							.withJSON("avatar",currentNode.path("avatar").toString())
							.withJSON("status",currentNode.path("status").toString())
							.withJSON("interview",currentNode.path("interview").toString())
							.withJSON("logs",currentNode.path("logs").toString())
							);
				}else if(interview.isEmpty()) {
					table.putItem(new Item().withPrimaryKey("idCan", id_can, "cmnd", cmnd)
							.withJSON("nameCan",currentNode.path("nameCan").toString())
							.withJSON("email",currentNode.path("email").toString())
							.withJSON("phone",currentNode.path("phone").toString())
							.withJSON("gender",currentNode.path("gender").toString())
							.withJSON("dob",currentNode.path("dob").toString())
							.withJSON("dateImport",currentNode.path("dateImport").toString())
							.withJSON("linkCV",currentNode.path("linkCV").toString())
							.withJSON("rate",currentNode.path("rate").toString())
							.withJSON("namePos",currentNode.path("namePos").toString())
							.withJSON("workExp",currentNode.path("workExp").toString())
							.withJSON("avatar",currentNode.path("avatar").toString())
							.withJSON("status",currentNode.path("status").toString())
							.withJSON("logs",currentNode.path("logs").toString())
							);
				}
				

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

	public static void loadPosition() throws JsonParseException, IOException {
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
			String requirement1 = currentNode.path("requirement").asText();
			String benefit1 = currentNode.path("benefit").asText();
			String description1 = currentNode.path("description").asText();
			
			String requirement = formatContent(requirement1);
			String benefit = formatContent(benefit1);
			String description = formatContent(description1);
			try {
				table.putItem(new Item().withPrimaryKey("idPos", idPos, "name", name)
						.withString("area",area)
						.withString("expDate",expDate)
						.withString("requirement",requirement)
						.withJSON("listQues",currentNode.path("listQues").toString())
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
	public static void loadQuestion() throws JsonParseException, IOException {
		Table table = dynamoDB.getTable("Questions");

		JsonParser parser = new JsonFactory().createParser(new File("Questions.json"));

		JsonNode rootNode = new ObjectMapper().readTree(parser);
		Iterator<JsonNode> iter = rootNode.iterator();

		ObjectNode currentNode;

		while (iter.hasNext()) {
			currentNode = (ObjectNode) iter.next();

			String idQues = currentNode.path("idQues").asText();
			String content = currentNode.path("content").asText();

			try {
				table.putItem(new Item().withPrimaryKey("idQues", idQues, "content", content)
						.withString("a",currentNode.path("a").asText())
						.withString("b",currentNode.path("b").asText())
						.withString("c",currentNode.path("c").asText())
						.withString("d",currentNode.path("d").asText())
						.withString("answer",currentNode.path("answer").asText())
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
	public static void loadUser() throws JsonParseException, IOException {
		Table table = dynamoDB.getTable("User");

		JsonParser parser = new JsonFactory().createParser(new File("userdata.json"));

		JsonNode rootNode = new ObjectMapper().readTree(parser);
		Iterator<JsonNode> iter = rootNode.iterator();

		ObjectNode currentNode;

		while (iter.hasNext()) {
			currentNode = (ObjectNode) iter.next();

			String id_user = currentNode.path("id_user").asText();
			String name_user = currentNode.path("tk").asText();

			try {
				table.putItem(new Item().withPrimaryKey("id_user", id_user, "tk", name_user)
						.withJSON("name_user",currentNode.path("name_user").toString())
						.withJSON("pass",currentNode.path("pass").toString())
						.withJSON("code",currentNode.path("code").toString())
						.withJSON("avatar",currentNode.path("avatar").toString())
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
	//xử "Chien-Pro-Haha" thành 
	//"- Chien
	// - Pro
	// - Haha "
	public static String formatContent(String content) {
		String[] ar = content.split("-");
		String result = "";
		for (int i = 0; i<ar.length;i++) {
			result = result + "-" + ar[i] + "\n";
		}
		return result;
	}
}
