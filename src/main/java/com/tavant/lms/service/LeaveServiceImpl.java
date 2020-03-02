package com.tavant.lms.service;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tavant.lms.dto.EmployeeDTO;
import com.tavant.lms.dto.LeaveDTO;
import com.tavant.lms.entity.Employee;
import com.tavant.lms.entity.Leave;
import com.tavant.lms.exceptions.DataNotFoundException;
import com.tavant.lms.exceptions.ExceptionConstants;
import com.tavant.lms.mapper.EmployeeMapper;
import com.tavant.lms.mapper.LeaveMapper;
import com.tavant.lms.repository.EmployeeRepository;
import com.tavant.lms.repository.LeaveRepository;
import com.tavant.lms.security.ExtractUserAuthentication;


@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
	LeaveRepository employeeLeaveRepository;
    
    @Autowired
	EmployeeRepository employeeRepository;

    @Override
    public Page<LeaveDTO> getAllLeaveRequest(Pageable pageable) {
    	
    	long authenticatedEmployeeId = ExtractUserAuthentication.getCurrentUser().getId();
    	Employee employee = employeeRepository.findById(authenticatedEmployeeId)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));
    	
        return employeeLeaveRepository.findAllByEmployee(employee, pageable)
                .map(LeaveMapper :: mapToDto );
    }
    
    @Override
    public Page<LeaveDTO> getAllLeavesToApprove(Pageable pageable) {
    	
    	long authenticatedEmployeeId = ExtractUserAuthentication.getCurrentUser().getId();
    	Employee employee = employeeRepository.findById(authenticatedEmployeeId)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));
    	
        return employeeLeaveRepository.findAllByToManagerAndStatus(employee, "PENDING", pageable)
                .map(LeaveMapper :: mapToDto );
    }

    @Override
    public LeaveDTO getEmployeeLeaveByLeaveId(Long id) {

        Leave employeeLeave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));
        return LeaveMapper.mapToDto(employeeLeave);
    }

    @Override
    public LeaveDTO createEmployeeLeave(LeaveDTO leaveDTO) {

        long employeeId = ExtractUserAuthentication.getCurrentUser().getId();
        
        EmployeeDTO employeeDTO = EmployeeMapper.mapToDto(employeeRepository.findById(employeeId)
        		.orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND)));
        leaveDTO.setEmployeeDTO(employeeDTO);	
        leaveDTO.setToManager(employeeDTO.getManager());		
        leaveDTO.setStatus("PENDING");
        
        Leave employeeLeave = employeeLeaveRepository.save(LeaveMapper.mapToEntity(leaveDTO));
        return LeaveMapper.mapToDto(employeeLeave);
    }

    @Override
    public LeaveDTO updateEmployeeLeave(LeaveDTO leaveDTO) {

        Leave returnedEmployeeLeave = employeeLeaveRepository.findById(leaveDTO.getLeaveId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));
        
        if(leaveDTO.getDeniedReason()!=null)
        	returnedEmployeeLeave.setDeniedReason(leaveDTO.getDeniedReason());

        returnedEmployeeLeave.setStatus(leaveDTO.getStatus());
        returnedEmployeeLeave.setEmployee(returnedEmployeeLeave.getEmployee());
        return LeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }

}
