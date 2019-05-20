package com.DynamoDb;

import java.util.Arrays;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class CreateTable {

	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();

        static DynamoDB dynamoDB = new DynamoDB(client);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createCandidate("Candidate_M");
		createCandidate("Candidate_S");
		createPosition();
		createQuestion();
		createUser();
	}

	public static void createCandidate(String tableName) {
		//Create table Canidate
        //String tableName = "Candidate_M";
        //String tableName = "Candidate_S";
        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                Arrays.asList(new KeySchemaElement("idCan", KeyType.HASH), // Partition key
                    new KeySchemaElement("cmnd", KeyType.RANGE)), // Sort key
                Arrays.asList(new AttributeDefinition("idCan", ScalarAttributeType.S),
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
	
	public static void createPosition() {
		String tableName = "Position";
        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                Arrays.asList(new KeySchemaElement("idPos", KeyType.HASH), // Partition key
                    new KeySchemaElement("name", KeyType.RANGE)), // Sort key
                Arrays.asList(new AttributeDefinition("idPos", ScalarAttributeType.S),
                    new AttributeDefinition("name", ScalarAttributeType.S)),
                new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }    
	}
	
	public static void createQuestion() {
		String tableName = "Questions";
        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                Arrays.asList(new KeySchemaElement("idQues", KeyType.HASH), // Partition key
                    new KeySchemaElement("content", KeyType.RANGE)), // Sort key
                Arrays.asList(new AttributeDefinition("idQues", ScalarAttributeType.S),
                    new AttributeDefinition("content", ScalarAttributeType.S)),
                new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }    
	}
	
	public static void createUser() {
		String tableName = "User";
        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                Arrays.asList(new KeySchemaElement("idUser", KeyType.HASH), // Partition key
                    new KeySchemaElement("userName", KeyType.RANGE)), // Sort key
                Arrays.asList(new AttributeDefinition("idUser", ScalarAttributeType.S),
                    new AttributeDefinition("userName", ScalarAttributeType.S)),
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
