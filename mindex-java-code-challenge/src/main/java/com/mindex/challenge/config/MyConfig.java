package com.mindex.challenge.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.mindex.challenge.Execute;

@Configuration
public class MyConfig {
	
	@Bean(name= {"myBean","myBean2"})
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
