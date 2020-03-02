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

import com.tavant.lms.dto.LeaveDTO;
import com.tavant.lms.service.LeaveService;

@RestController
@RequestMapping
public class LeaveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveController.class);

    @Autowired
    LeaveService employeeLeaveService;
    
    /*
     * This will return all the leave applied by you.
     */
    @GetMapping("/leaveRequests")
    public ResponseEntity getAllLeaveRequest(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        LOGGER.info("API Retrieve all Leaves Request");
        return new ResponseEntity<>(employeeLeaveService.getAllLeaveRequest(pageable), HttpStatus.OK);
    }
    
    /*
     * This will return all the leave request applied to you for approval
     */
    @GetMapping("/leavesToApprove")
    public ResponseEntity getAllLeavesToApprove(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        LOGGER.info("API Retrieve all Leaves To Approve");
        return new ResponseEntity<>(employeeLeaveService.getAllLeavesToApprove(pageable), HttpStatus.OK);
    }
    
    /*
     * This will return leave by id.
     */
    @GetMapping("/leaves/{id}")
    public ResponseEntity getEmployeeLeaveByLeaveId(@PathVariable long id){

        LOGGER.info("API Retrieve single Leave");
        return new ResponseEntity<>(employeeLeaveService.getEmployeeLeaveByLeaveId(id), HttpStatus.OK);
    }
    
    /*
     * This will create a leave
     */
    @PostMapping("/createLeave")
    public ResponseEntity createEmployeeLeave(@RequestBody LeaveDTO leaveDTO){

        LOGGER.info("API Create Leave Request");
        return new ResponseEntity<>(employeeLeaveService.createEmployeeLeave(leaveDTO), HttpStatus.OK);
    }
    
    /*
     * This will update a leave.
     */
    @PutMapping("/updateLeave")
    public ResponseEntity updateEmployeeLeave(@RequestBody LeaveDTO leaveDTO){

        LOGGER.info("API update Leave");
        return new ResponseEntity<>(employeeLeaveService.updateEmployeeLeave(leaveDTO), HttpStatus.OK);
    }

}
