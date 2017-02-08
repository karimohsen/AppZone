package com.appzone.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource; 
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select email,password,true from users where email = ?")
		.authoritiesByUsernameQuery("select users.email,roles.rolename from users,roles where users.roleid=roles.id and users.email = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
        .antMatchers("/login*").anonymous()
        .anyRequest().authenticated()
        .and()
		.formLogin()
        .failureUrl("/login.html?error=true")
        .and()
        .logout().logoutSuccessUrl("/login.html")
		.and().csrf().disable()
		.authorizeRequests()
		.antMatchers("/writecomment","/showallcomments").hasRole("USER")
		.antMatchers("/confirmcomments").hasAnyRole("MODERATOR")
		.anyRequest().permitAll();
	}
	
	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/appzone");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}


}
