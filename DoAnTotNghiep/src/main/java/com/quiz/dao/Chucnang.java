package com.quiz.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Chucnang {
	public static void main(String[] args) {
		DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
		Date datecurrent = new Date();
		Date datenew = null;
		try {
			datenew =(Date)dateformat.parse("13/05/2019 13:50:48.639");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar now = Calendar.getInstance();
		Calendar continue1 = Calendar.getInstance();
		now.setTime(datecurrent);
		continue1.setTime(datecurrent);
		
		now.add(Calendar.MINUTE, 20);
		System.out.println((now.getTimeInMillis()-continue1.getTimeInMillis())/(1*60*1000));
		
		
	}
}