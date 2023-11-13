package com.mindex.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.controller.EmployeeController;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;




@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChallengeApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
    private String employeeUrl;
    private String employeeIdUrl;	
	@Autowired
    private EmployeeRepository employeeRepository;
	
	@LocalServerPort
	 private int port;
	
	@Autowired
	EmployeeController EC;
	
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        LOG.debug("This is the rest url " + employeeUrl);
        LOG.debug("This is the rest service " + employeeIdUrl);
    }

    
	@Test
	public void contextLoads() {
	System.out.println("herererer");

}
	
    @Test
    public void test() {
        Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
     
        System.out.println(employee.getFirstName());
        System.out.println(employee.getLastName());
        System.out.println(employee.getPosition());
        System.out.println(employee.getDepartment());
        
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("821");
        testEmployee.setFirstName("Halston");
        testEmployee.setLastName("Hendricks");
        testEmployee.setDepartment("Programmer");
        testEmployee.setPosition("CS");
        
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
	      
        System.out.println("CreatedEmployee "+ createdEmployee.getEmployeeId() + "TestEmployee " + testEmployee.getEmployeeId());
        
     
    }

}
