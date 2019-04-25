package com.quiz.dao;

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
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.entity.Candidate;
import com.entity.Questions;
import com.quiz.form.QuesPos;

@Repository
public class QuizDao {
	
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();

        DynamoDB dynamoDB = new DynamoDB(client);
        
        // Hàm lấy nội dụng questition theo position
        public ArrayList<Questions> getQuestionsQuiz (String idPos) {
    		ArrayList<Questions> ds = new ArrayList<Questions>();
    		QuesPos quespos = getQuestionsofPosition(idPos);
    		
    		for (int i = 0; i < quespos.getListQues().size(); i++) {
				Questions quest = getQuestionsContent(quespos.getListQues().get(i).toString());
				ds.add(quest);
			}
    		return ds;
    	}
        
        // Hàm lấy danh sách id questiton theo id position
        public QuesPos getQuestionsofPosition (String idPos) {
    		ArrayList<Questions> ds = new ArrayList<Questions>();
    		QuesPos quespos = null;
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
        		quespos =new QuesPos(item.get("idPos").toString(),listques);
        		
    		}
    		return quespos;
        }
        
        // Lấy nôi dung questition dự vào idQues
        public Questions getQuestionsContent (String idQues) {
        	Questions quest =null;
    		
    		Table table = dynamoDB.getTable("Questions");
    		
    		QuerySpec spec = new QuerySpec()
        			.withKeyConditionExpression("idQues = :id_ques")
        			.withValueMap(new ValueMap()
        					.withString(":id_ques",idQues))
        			.withConsistentRead(true);
    		ItemCollection<QueryOutcome> items =table.query(spec);
    		Iterator<Item> iterator = items.iterator();
    			while (iterator.hasNext()) {
    				Item item = iterator.next();
    				quest = new Questions( item.getString("idQues")
    									  , item.getString("content")
    									  , item.getString("a")
    									  , item.getString("b")
    									  , item.getString("c")
    									  , item.getString("d")
    									  , item.getString("answer"));
				}
    		return quest;
    	}
        
        public Candidate getCandidateById_S(String id) {
        	Table table = dynamoDB.getTable("Candidate_S");
        	Candidate candidate=null;
        	QuerySpec spec = new QuerySpec()
        	    .withKeyConditionExpression("idCan = :v_id")
        	    .withValueMap(new ValueMap()
        	        .withString(":v_id",id));

        	ItemCollection<QueryOutcome> items = table.query(spec);

        	Iterator<Item> iterator = items.iterator();
        	Item item = null;
        	while (iterator.hasNext()) {
        	    item = iterator.next();
        	    candidate =new Candidate(item.getString("idCan"),"",item.getString("cmnd") ,"","",true,"","","","","","",null,"",null,null,null);
        	}
        	return candidate;
        }
        
        // kiểm tra bài test của cadidate
        public boolean CheckQuest(String idQues,String answer) {
        	Questions ques = getQuestionsContent(idQues);
        	if(ques.getAnswer().equals(answer))
        		return true;
        	return false;
        }
        //Lấy tổng số câu hỏi theo position
        public int getTotalQuestofPpsition(String idPos) {
        	int total = getQuestionsofPosition(idPos).getListQues().size();
        	return total;
        }
 }