package com.tavant.lms.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tavant.lms.dto.EmployeeDTO;
import com.tavant.lms.entity.Employee;
import com.tavant.lms.exceptions.DataNotFoundException;
import com.tavant.lms.exceptions.ExceptionConstants;
import com.tavant.lms.exceptions.RegistrationFormException;
import com.tavant.lms.mapper.EmployeeMapper;
import com.tavant.lms.repository.EmployeeRepository;
import com.tavant.lms.security.ExtractUserAuthentication;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {

		return employeeRepository.findAll(pageable).map(EmployeeMapper::mapToDto);
	}

	@Override
	public EmployeeDTO getAuthenticatedEmployee() {

		long authenticatedEmployeeId = ExtractUserAuthentication.getCurrentUser().getId();
		Employee employee = employeeRepository.findById(authenticatedEmployeeId)
				.orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));
		return EmployeeMapper.mapToDto(employee);
	}

	@Override
	public EmployeeDTO getEmployeeById(Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_RECORD_NOT_FOUND));
		return EmployeeMapper.mapToDto(employee);
	}

	@Override
	public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

		if (employeeDTO.getUsername() == null)
			throw new RegistrationFormException(ExceptionConstants.EMPLOYEE_USERNAME_CANNOT_EMPTY);

		if (employeeRepository.findByUsername(employeeDTO.getUsername()) != null)
			throw new RegistrationFormException(ExceptionConstants.EMPLOYEE_USERNAME_ALREADY_USED);

		employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
		employeeDTO.setRole(employeeDTO.getRole());
		employeeDTO.setPhoneNumber(employeeDTO.getPhoneNumber());
		Employee employee = employeeRepository.save(EmployeeMapper.mapToEntity(employeeDTO));
		return EmployeeMapper.mapToDto(employee);
	}

	@Override
	public List<EmployeeDTO> getAllAdmin() {

		return employeeRepository.findAllByRole("ROLE_ADMIN").stream().map(EmployeeMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
		Employee employee = EmployeeMapper.mapToEntity(employeeDTO);
		return EmployeeMapper.mapToDto(employeeRepository.save(employee));
	}

}
