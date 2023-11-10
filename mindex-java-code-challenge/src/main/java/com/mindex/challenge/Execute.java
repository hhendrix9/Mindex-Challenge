package com.mindex.challenge;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

import com.mindex.challenge.config.MyConfig;
import com.mindex.challenge.controller.EmployeeController;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.impl.EmployeeServiceImpl;

@Component
public class Execute {
	
	private static final Logger LOG = LoggerFactory.getLogger(Execute.class);
	private String employeeUrl="http://localhost:8174/employee";
	private String employeeUrl2="http://localhost:8174/compensation";
    private String employeeUrl3= "http://localhost:8185/compensation/{id}";
	private String employeeUrl4="http://localhost:8079/NumOfReports/{id}";
    
    public void testCreateReadUpdate() {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfig.class);
    	RestTemplate restTemplate  = ctx.getBean(RestTemplate.class);    
    	
    	
    	Employee testEmployee = new Employee();
	        testEmployee.setFirstName("Eric");
	        testEmployee.setLastName("Baker");
	        testEmployee.setDepartment("Engineering");
	        testEmployee.setPosition("Developer");
	   
	        // Test web services
	        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
	        Employee compensation= restTemplate.postForEntity(employeeUrl2, createdEmployee, Employee.class).getBody();
	        Employee getCompensation = restTemplate.postForEntity(employeeUrl3,compensation.getEmployeeId(), Employee.class, compensation.getEmployeeId()).getBody();
	        Employee numOfReport= restTemplate.postForEntity(employeeUrl4,"16a596ae-edd3-4847-99fe-c4518e82c86f", Employee.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
	    
	        
	        
	      
	  }
}
