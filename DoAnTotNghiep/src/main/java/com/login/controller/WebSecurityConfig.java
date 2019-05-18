package com.login.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    
         
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/index","/profile").hasAnyRole("HR","ADMIN","INTERVIEWER")
			.antMatchers("/position").hasAnyRole("HR","ADMIN")
			.antMatchers("/userMgmt").hasAnyRole("HR","ADMIN")
			.antMatchers("/homeApply","/quiz").permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/401")
			.and().formLogin()
			.loginProcessingUrl("/Login_user")
			.loginPage("/Login")
			.failureUrl("/Login")
			.defaultSuccessUrl("/index")
			.usernameParameter("username")
			.passwordParameter("password")
			.and().logout()
			.logoutUrl("/Logout").logoutSuccessUrl("/Login");
		
	}
}
