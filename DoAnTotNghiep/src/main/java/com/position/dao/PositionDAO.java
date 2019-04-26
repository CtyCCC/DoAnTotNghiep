package com.position.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.entity.Position;
import com.entity.Questions;
import com.quiz.form.QuesPos;

@Repository
public class PositionDAO {

	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
			.build();

	DynamoDB dynamoDB = new DynamoDB(client);


	public ArrayList<Position> getAllPosition () {
		ArrayList<Position> ds = new ArrayList<Position>();
		ScanResult rs = null;
		do {
			ScanRequest req = new ScanRequest();
			req.withTableName("Position");
			if(rs != null){
				req.setExclusiveStartKey(rs.getLastEvaluatedKey());
			}
			rs = client.scan(req);
			List<Map<String, AttributeValue>> rows = rs.getItems();

			for(Map<String, AttributeValue> map : rows){
				try{
					//AttributeValue v = map.get("id_can");
					String idPos = map.get("idPos").getS();
					String name = map.get("name").getS();
					String area= map.get("area").getS();
					String expDate= map.get("expDate").getS();
					String requirement= map.get("requirement").getS();
					String benefit= map.get("benefit").getS();
					String description= map.get("description").getS();
					Position pos = new Position(idPos, name, area, expDate, requirement, benefit, description);
					ds.add(pos);
				} catch (NumberFormatException e){
					System.out.println(e.getMessage());
				}
			}

		}while(rs.getLastEvaluatedKey() != null);
		return ds;
	}

	//lấy position theo name, ko tốn công query trong db lại vì sài thằng arr của thằng getAll
	public Position getOnePositionByName (String name, ArrayList<Position> arr) {
		for (Position position : arr) {
			if (position.getName().equals(name))
				return position;
		}
		return null;
	}

	//get pos theo id
	public Position getOnePositionByID (String idPos, ArrayList<Position> arr) {
		for (Position position : arr) {
			if (position.getIdPos().equals(idPos))
				return position;
		}
		return null;
	}

	// đưa vào mảng Position, định dạng lại 3 thằng Requirement, benefit, description
	// của toàn bộ Position trong mảng
	// thành kiểu như hàm dưới, in cho đẹp
	public void formatAllResult(ArrayList<Position> arr) {
		for (Position position : arr) {
			String re = position.getRequirement();
			position.setRequirement(formatContent(re));
			String be = position.getBenefit();
			position.setBenefit(formatContent(be));
			String des = position.getDescription();
			position.setDescription(formatContent(des));
		}
	}

	//xử "Chien-Pro-Haha" thành 
	//"- Chien
	// - Pro
	// - Haha "
	public String formatContent(String content) {
		String[] ar = content.split("-");
		String result = "";
		for (int i = 0; i<ar.length;i++) {
			result = result + "-" + ar[i] + "\n";
		}
		return result;
	}

	//tự tăng id Pos
	public String autoIdPos(ArrayList<Position> arr) {
		int count = arr.size()+1;
		return "POS"+count+"";
	}

	public void addNewPos(Position p) {

		Table table = dynamoDB.getTable("Position");
		String arr = p.getListQues().toString();
		try {
			System.out.println("Adding a new item...");
			table.putItem(new Item().withPrimaryKey("idPos", p.getIdPos(), "name", p.getName())
					.withString("area",p.getArea())
					.withString("expDate",p.getExpDate())
					.withString("requirement",p.getRequirement())
					.withString("benefit",p.getBenefit())
					.withString("listQues", arr)
					.withString("description",p.getDescription()));
					
			System.out.println("PutItem succeeded:" + p);

		}
		catch (Exception e) {
			System.err.println("Unable to add item: " + p.getName());
			System.err.println(e.getMessage());
		}

	}

	//update
	public void updatePos(Position p) {

		Table table = dynamoDB.getTable("Position");

		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("idPos", p.getIdPos(), "name", p.getName())
				.withUpdateExpression("set area = :a, expDate = :b, requirement= :c, benefit = :d, description = :e")
				.withValueMap(new ValueMap().withString(":a", p.getArea())
						.withString(":b", p.getExpDate())
						.withString(":c", p.getRequirement())
						.withString(":d", p.getBenefit())
						.withString(":e", p.getDescription()))
				.withReturnValues(ReturnValue.UPDATED_NEW);

		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

		}
		catch (Exception e) {
			System.err.println("Unable to update item: " + p.getName());
			System.err.println(e.getMessage());
		}
	}
	
	//lấy list idQues theo id Pos
	public List<Object> getQuestionsOfPosition (String idPos) {
		
		Table table = dynamoDB.getTable("Position");
		List<Object> listques= new ArrayList<>();
		
		QuerySpec spec = new QuerySpec()
						.withKeyConditionExpression("idPos =:v_id")
						.withValueMap(new ValueMap()
								.withString(":v_id",idPos));
		ItemCollection<QueryOutcome> items = table.query(spec);
		
		Iterator<Item> iterator =items.iterator();
		Item item = null;
		while (iterator.hasNext()) {
			
			item = iterator.next();
			listques = (List<Object>) item.getList("listQues");
    			
		}
		return listques;
    }
	
	public void updateQuesPos(String id, String name, String arr) {

		Table table = dynamoDB.getTable("Position");

		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("idPos",id, "name", name)
				.withUpdateExpression("set listQues = :a")
				.withValueMap(new ValueMap().withString(":a", arr.toString()))
				.withReturnValues(ReturnValue.UPDATED_NEW);

		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

		}
		catch (Exception e) {
			System.err.println("Unable to update item: " + name);
			System.err.println(e.getMessage());
		}
	}
	
}
