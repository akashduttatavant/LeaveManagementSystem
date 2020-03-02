package com.tavant.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tavant.lms.dto.LeaveDTO;


public interface LeaveService {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    Page<LeaveDTO> getAllLeaveRequest(Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    Page<LeaveDTO> getAllLeavesToApprove(Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    LeaveDTO getEmployeeLeaveByLeaveId(Long id);
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    LeaveDTO createEmployeeLeave(LeaveDTO leaveDTO);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    LeaveDTO updateEmployeeLeave(LeaveDTO leaveDTO);

}
