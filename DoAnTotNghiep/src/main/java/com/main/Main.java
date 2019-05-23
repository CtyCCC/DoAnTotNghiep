package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.apply.Apply;
import com.candidate.DoAnTotNghiepApplication;
import com.chart.Chart;
import com.login.Login;
import com.position.PositionSC;
import com.quiz.Quiz;
import com.usermanagement.UserSC;


@SpringBootApplication
@Import({Login.class,
		PositionSC.class, 
		DoAnTotNghiepApplication.class,
		Apply.class, 
		Quiz.class,
		UserSC.class,
		Chart.class})
public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Main.class, args);
	}

}
