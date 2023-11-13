package com.mindex.challenge;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
public class ChallengeApplication {	
	 private static final Logger LOG = LoggerFactory.getLogger(ChallengeApplication.class);
	public static void main(String[] args) {
		LOG.debug("Fiiirst Paaart");
		SpringApplication.run(ChallengeApplication.class, args);	

		//Class that tests my websevices
		Execute ex = new Execute();
		ex.testCreateReadUpdate();
     }
}
