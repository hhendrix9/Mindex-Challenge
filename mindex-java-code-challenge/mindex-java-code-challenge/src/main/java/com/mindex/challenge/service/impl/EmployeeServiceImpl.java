package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);
        
        LOG.debug("employee created!!!");

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

//       if(employeeRepository.findByEmployeeId(employee.getEmployeeId()) != null) 
//    	   
////       {
//    	   Employee employee2 = new Employee();
//    	   employee2=employeeRepository.findByEmployeeId(employee.getEmployeeId());
//    	   LOG.debug("Up in hheere [{}]", employee + "   " + employee2);
//    	   System.out.println(employee2.getEmployeeId());
//    	   employeeRepository.deleteById(employee2.getEmployeeId());
//    	}
       
       
       
       
       LOG.debug("Made it here tho");
        
       return employeeRepository.save(employee);
    }
    
    
}
