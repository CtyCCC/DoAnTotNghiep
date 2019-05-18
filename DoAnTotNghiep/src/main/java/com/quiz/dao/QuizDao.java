package com.quiz.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
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
        // Lấy thông tin của cadidate trong bản cadidate Sub
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
       // add timestart test khi người dùng bắt đầu test
        public boolean addTimeStart(String id) {
        	Table table = dynamoDB.getTable("Candidate_S");
        	DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
    		Date datecurrent = new Date();
    		Date endDateTest = timeEndTest(datecurrent);
        	Candidate can = getCandidateById_S(id);
        	
        	UpdateItemSpec updateitem = new UpdateItemSpec()
        								.withPrimaryKey("idCan",can.getIdCan(),"cmnd",can.getCmnd())
        								.withUpdateExpression("set dateImport =:val")
        								.withValueMap(new ValueMap().withString(":val",dateformat.format(endDateTest)))
        								.withReturnValues(ReturnValue.UPDATED_NEW);
        	
        	UpdateItemOutcome outcome = table.updateItem(updateitem);
        	return true;
        }
        
        // Xác định thời gian kết thúc bài test 
        public Date timeEndTest(Date date) {
        	Calendar uptime = Calendar.getInstance();
        	uptime.setTime(date);
        	uptime.add(Calendar.MINUTE, 30);
        	Date endtime = uptime.getTime(); 
        	return endtime;
        }
        
        //Xác định thời gian test còn lại
        /** Note: khi người dùng đăng nhập lại link test
         * Kiểm tra họ còn test được hay không 
        **/
        public long Timeremaining(String id) {
        	DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
        	Date datecurrent = new Date();
        	long timeremaining= 0;

        	Candidate can = getCandidateById_S(id);
        	// khởi tạo calender
        	Calendar Tnow = Calendar.getInstance();
        	Calendar Tdatabase = Calendar.getInstance();
        	// set giá trị
        	try {
        		Tnow.setTime(datecurrent);
            	Tdatabase.setTime(dateformat.parse(can.getDateImport()));
			} catch (Exception e) {
				// TODO: handle exception
			}
        	//Kiểm tra thời gian
        	if(!Tnow.after(Tdatabase)) {
        		timeremaining =(Tdatabase.getTimeInMillis() - Tnow.getTimeInMillis());
        		if(timeremaining<=0 || timeremaining > 30)
        			return 0;
        		return timeremaining;
        	}
        	return timeremaining;
        }
 }