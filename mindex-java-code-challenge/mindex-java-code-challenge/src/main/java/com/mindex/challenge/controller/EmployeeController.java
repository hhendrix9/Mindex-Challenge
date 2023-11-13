package com.mindex.challenge.controller;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

import java.awt.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class EmployeeController {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;
	
    @Autowired
    private EmployeeRepository employeeRepository;


	@PostMapping("/employee")
	public Employee create(@RequestBody Employee employee) {
		LOG.debug("Received employee create request for [{}]", employee);

		return employeeService.create(employee);
	}
	
	//Put Service to store compensation type and fields associated with it. Adds to Employee Object. Below is a sample body.
//	        {"employeeId" : "16a596ae-edd3-4847-99fe-c4518e82c86f",
//		    "salary" : "150,000",
//		    "effectiveDate" : "12/12/2023"}
	
      @PutMapping("/compensation/{id}")
	public Employee createCompensation(@PathVariable String id, @RequestBody Compensation comp) {
		LOG.debug("Received compensation create request for [{}]", comp);

	//set compensation variables to new Employye Object
	Employee emp = new Employee();
	emp.setCompensation(comp);


	//Need to copy the existing employee instance and into the new Employee object and then 
	//delete the previous instance to avoid conflicts
	Employee Employee2 = new Employee();
	Employee2=employeeRepository.findByEmployeeId(id);
	emp.setFirstName(Employee2.getFirstName());
	emp.setLastName(Employee2.getLastName());
	emp.setPosition(Employee2.getPosition());
	emp.setDepartment(Employee2.getDepartment());
	emp.setDirectReports(Employee2.getDirectReports());
	
	//Delete old instance and save new instance
	employeeRepository.delete(employeeRepository.findByEmployeeId(id));
	emp.setEmployeeId(id);
	return employeeRepository.save(emp);
	}
      
      
//Get service to retrieve the entire employee object
	@GetMapping("/employee/{id}")
	public Employee read(@PathVariable String id) {
		LOG.debug("Received employee create request for id [{}]", id);

		return employeeService.read(id);
	}

//Get Service to Retrieve the compensation Type and all the fields   
	@GetMapping("/compensation/{id}")
	public Compensation readComp(@PathVariable String id) {
		LOG.debug("Received compensation create request for id [{}]", id);
		
		//Returns the compensation object 
		Employee emp = new Employee();
		emp = employeeService.read(id);
		Compensation comp = new Compensation();
		comp = emp.getCompensation();
		
	return comp;
	
	}

// Updates employee after changes are made	
	@PutMapping("/employee/{id}")
	public Employee update(@PathVariable String id, @RequestBody Employee employee) {
		LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

		employee.setEmployeeId(id);
		return employeeService.update(employee);
	}

	//This Post Mapping service determines the number of total reports for and employee and records the value 
	// in a field in the Reporting Structure Type
	
	@PostMapping("/NumOfReports/{id}")
	public Employee updateNumberOfReports(@PathVariable String id) {
		LOG.debug("Received Number of Reports request for id [{}] and employee [{}]", id);

		Employee emp = new Employee();
		emp = employeeService.read(id);

		//number of direct reports you have
		int size = emp.getDirectReports().size();
        
		int counter = 0;
		int TotalSize = 0;
		
		//Loops through your direct reports and checks for their direct reports also
		if (emp.getDirectReports()!=null) {
			for (int i = 0; i < size; i++) {
				Employee newEmp = employeeService.read(emp.getDirectReports().get(i).getEmployeeId());
				if (newEmp.getDirectReports() != null) {
					counter += newEmp.getDirectReports().size();
				}
			}
		}

		TotalSize = size + counter;

		//Creates Reporting Structure object and adds to list which will update the fields
		ArrayList<ReportingStructure> ReportingStructure = new ArrayList<ReportingStructure>();
		ReportingStructure rs = new ReportingStructure();
		rs.setEmployeeId(emp.getEmployeeId());
		rs.setnumberOfReports(Integer.toString(TotalSize));
		ReportingStructure.add(rs);
		emp.setReportingStructure(ReportingStructure);
		
		//Need to copy the existing employee instance and into the new Employee object and then 
		//delete the previous instance to avoid conflicts
		Employee Employee2 = new Employee();
		Employee2=employeeRepository.findByEmployeeId(id);
		emp.setFirstName(Employee2.getFirstName());
		emp.setLastName(Employee2.getLastName());
		emp.setPosition(Employee2.getPosition());
		emp.setDepartment(Employee2.getDepartment());
		emp.setCompensation(Employee2.getCompensation());
		
		//Delete old instance and save new instance
		employeeRepository.delete(employeeRepository.findByEmployeeId(id));
		emp.setEmployeeId(id);
		return employeeRepository.save(emp);

	}

}
