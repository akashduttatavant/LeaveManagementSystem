package com.tavant.lms.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tavant.lms.dto.EmployeeDTO;
import com.tavant.lms.service.EmployeeService;

@RestController
@RequestMapping
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    /*
     * This will retrieve all the employees
     */
    @GetMapping("/employee")
    public ResponseEntity getAllEmployees(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        LOGGER.info("API Return all Employee´s");
        return new ResponseEntity<>(employeeService.getAllEmployees(pageable), HttpStatus.OK);
    }

    /*
     * This will return the logged in employee.
     */
    @GetMapping("/employee/me")
    public ResponseEntity getAuthenticatedEmployee() {

        LOGGER.info("API Return Authenticated Employee Profile");
        return new ResponseEntity<>(employeeService.getAuthenticatedEmployee(), HttpStatus.OK);
    }
    
    /*
     * This will return a employee by id.
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity getEmployeeById(@PathVariable long id) {

        LOGGER.info("API Return single Employee");
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }
    
    /*
     * This will create a employee
     */
    @PostMapping("/createEmployee")
    public ResponseEntity createEmployee(@RequestBody EmployeeDTO employeeDTO) {

        LOGGER.info("API Return created Employee");
        return new ResponseEntity<>(employeeService.createEmployee(employeeDTO), HttpStatus.OK);
    }
    

    /*
     * Update a employee
     */
    @PutMapping("/updateEmployee")
    public ResponseEntity updateEmployee(@RequestBody EmployeeDTO employeeDTO) {

        LOGGER.info("API Return updated Employee");
        return new ResponseEntity<>(employeeService.updateEmployee(employeeDTO), HttpStatus.OK);
    }
    
    /*
     * This will retrieve all the employee with role = 'ROLE_ADMIN'
     */
    @GetMapping("/admin")
    public ResponseEntity getAllAdmin() {

        LOGGER.info("API Return Admin Employee");
        return new ResponseEntity<>(employeeService.getAllAdmin(), HttpStatus.OK);
    }

}
