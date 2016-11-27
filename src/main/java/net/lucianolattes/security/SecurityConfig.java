package net.lucianolattes.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;
  
  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//    auth.jdbcAuthentication().dataSource(dataSource)
//        .usersByUsernameQuery("SELECT username, password FROM users WHERE username = ?")
//        .authoritiesByUsernameQuery("SELECT username, role FROM user_roles WHERE username = ?");
    auth.userDetailsService(authenticationService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().antMatchers("/api/**").hasAnyRole("ADMIN", "USER").anyRequest().permitAll().and().httpBasic();
  }
}
