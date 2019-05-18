package com.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userdetail;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userdetail);
	}
	
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
			.exceptionHandling().accessDeniedPage("/403")
			.and().formLogin()
			.loginProcessingUrl("/Login_user")
			.loginPage("/Login")
			.failureUrl("/Loginfail")
			.defaultSuccessUrl("/default")
			.usernameParameter("username")
			.passwordParameter("password")
			.and().logout()
			.logoutUrl("/Logout").invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/Login");
		
	}
}
