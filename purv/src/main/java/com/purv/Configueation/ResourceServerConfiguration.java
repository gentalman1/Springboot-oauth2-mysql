package com.purv.Configueation;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

	
	  private static final String RESOURCE_ID = "resource-server-rest-api"; 
	/*
	 * static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')"; private
	 * static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
	 * private static final String SECURED_PATTERN = "/secured/**";
	 */
	 
  
	  private String [] ignoredPaths = new String[]{
         //"/oauth/token","/user"//completely public links should be added here
  };
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*http.requestMatchers()
                .antMatchers(SECURED_PATTERN).and().authorizeRequests()
                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
                .anyRequest().access(SECURED_READ_SCOPE);*/
		
		/* http.antMatcher("/**") .authorizeRequests().anyRequest().authenticated(); */
		 
     http.authorizeRequests()
          .antMatchers(ignoredPaths).permitAll()
          .anyRequest().authenticated()
          .and()
          .httpBasic();   
    }
}
