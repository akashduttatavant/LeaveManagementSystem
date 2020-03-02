package com.tavant.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tavant.lms.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    Page<EmployeeDTO> getAllEmployees(Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    EmployeeDTO getAuthenticatedEmployee();

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    EmployeeDTO getEmployeeById(Long id);
    
    // only allowed to admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    
    // only allowed to admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);
    
    // only allowed to admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<EmployeeDTO> getAllAdmin();


}
