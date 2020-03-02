package com.tavant.lms.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import com.tavant.lms.entity.Employee;
import com.tavant.lms.entity.Leave;


@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
	
	Page<Leave> findAllByEmployee(Employee employee, Pageable pageable);
	
	Page<Leave> findAllByToManagerAndStatus(Employee employee, String status, Pageable pageable);
}
