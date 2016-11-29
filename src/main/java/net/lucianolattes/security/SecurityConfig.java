package net.lucianolattes.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @SuppressWarnings("unused")
  @Autowired
  private DataSource dataSource;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder authn) throws Exception {
    ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
    authn.userDetailsService(authenticationService).passwordEncoder(encoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().antMatchers("/api/**").hasAnyRole("ADMIN", "USER").anyRequest()
        .permitAll().and().httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  public static void main(String[] args) {
    ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
    String[] usernames = { "luciano", "thousandeyes", "twitter", "johndoe", "iamdevloper" };
    for (String username : usernames) {
      String encodedPassword = encoder.encodePassword(username, null);
      System.out.println(username + " : " + encodedPassword);
    }
  }
}
