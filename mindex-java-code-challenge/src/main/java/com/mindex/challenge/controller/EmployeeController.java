package com.mindex.challenge.controller;

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

	@PostMapping("/employee")
	public Employee create(@RequestBody Employee employee) {
		LOG.debug("Received employee create request for [{}]", employee);

		return employeeService.create(employee);
	}
	
	//Put Service to store compensation type and fields associated with it
      @PutMapping("/compensation/{id}")
	public Employee createCompensation(@PathVariable String id, @RequestBody Compensation comp) {
		LOG.debug("Received compensation create request for [{}]", comp);

		//set compensation variables
		Employee emp = employeeService.read(id);
		ArrayList<Compensation> c = new ArrayList<Compensation>();
		c.add(comp);
		emp.setCompensation(c);
		
		emp.setEmployeeId(id);
		return employeeService.update(emp);
	}

	@GetMapping("/employee/{id}")
	public Employee read(@PathVariable String id) {
		LOG.debug("Received employee create request for id [{}]", id);

		return employeeService.read(id);
	}

	//Get Service to Retrieve the compensation Type and all the fields   
	
	@GetMapping("/compensation/{id}")
	public Employee readComp(@PathVariable String id) {
		LOG.debug("Received compensation create request for id [{}]", id);
		
	return employeeService.read(id);
	
	}

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

		System.out.println("That EMP BEE " + emp.getDirectReports());

		//Loops through your direct reports and checks other employees for sub reports
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
		rs.setEmployee(emp.getFirstName());
		rs.setnumberOfReports(Integer.toString(TotalSize));
		ReportingStructure.add(rs);
		emp.setReportingStructure(ReportingStructure);
		return employeeService.update(emp);

	}

}
