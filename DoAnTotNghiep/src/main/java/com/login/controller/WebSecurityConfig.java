package com.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
 
        // Sét đặt dịch vụ để tìm kiếm User trong Database.
        // Và sét đặt PasswordEncoder.
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());   
	}
        @Override
        protected void configure(HttpSecurity http) throws Exception {
     
            http.csrf().disable();
     
            // Các trang không yêu cầu login
            http.authorizeRequests().antMatchers("/", "/login", "/homeApply","/quiz").permitAll();
     
            // Trang /userInfo yêu cầu phải login với vai trò ROLE_USER hoặc ROLE_ADMIN.
            // Nếu chưa login, nó sẽ redirect tới trang /login.
            http.authorizeRequests().antMatchers("/").access("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN')");
     
            // Khi người dùng đã login, với vai trò XX.
            // Nhưng truy cập vào trang yêu cầu vai trò YY,
            // Ngoại lệ AccessDeniedException sẽ ném ra.
            http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
     
            // Cấu hình cho Login Form.
            http.authorizeRequests().and().formLogin()//
                    // Submit URL của trang login
                    .loginProcessingUrl("/Login") // Submit URL
                    .loginPage("/Login")//URL Page Login
                    .defaultSuccessUrl("/index")//Page login success
                    .failureUrl("/Login?error=true")//page login fail
                    .usernameParameter("username")
                    .passwordParameter("password")
                    // Cấu hình cho Logout Page.
                    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/Login");
     
 
    }
}
