package com.oneenterprise.employee.service;

import com.oneenterprise.employee.client.DepartmentClient;
import com.oneenterprise.employee.dto.*;
import com.oneenterprise.employee.entity.Employee;
import com.oneenterprise.employee.exception.ResourceNotFoundException;
import com.oneenterprise.employee.repository.EmployeeRepository;
import feign.FeignException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {
	private final EmployeeRepository repository;
	private final DepartmentClient departmentClient;

	public EmployeeService(EmployeeRepository repository, DepartmentClient departmentClient) {
		this.repository = repository;
		this.departmentClient = departmentClient;
	}

	public EmployeeResponse create(EmployeeRequest r) {
		String email = r.email().trim().toLowerCase();
		if (repository.existsByEmailIgnoreCase(email))
			throw new IllegalArgumentException("Email already exists: " + email);
		validateDepartment(r.departmentId());
		return response(repository.save(new Employee(r.name().trim(), email, r.departmentId())));
	}

	@Transactional(readOnly = true)
	public List<EmployeeResponse> findAll() {
		return repository.findAll().stream().map(this::response).toList();
	}

	@Transactional(readOnly = true)
	public EmployeeResponse findById(Long id) {
		return response(entity(id));
	}

	public EmployeeResponse update(Long id, EmployeeRequest r) {
		Employee e = entity(id);
		String email = r.email().trim().toLowerCase();
		if (!e.getEmail().equalsIgnoreCase(email) && repository.existsByEmailIgnoreCase(email))
			throw new IllegalArgumentException("Email already exists: " + email);
		validateDepartment(r.departmentId());
		e.setName(r.name().trim());
		e.setEmail(email);
		e.setDepartmentId(r.departmentId());
		return response(repository.save(e));
	}

	public void delete(Long id) {
		repository.delete(entity(id));
	}

	private Employee entity(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
	}

	private void validateDepartment(Long id) {
		try {
			departmentClient.getDepartmentById(id);
		} catch (FeignException.NotFound e) {
			throw new ResourceNotFoundException("Department not found: " + id);
		}
	}

	private EmployeeResponse response(Employee e) {
		DepartmentSummary d = departmentClient.getDepartmentById(e.getDepartmentId());
		return new EmployeeResponse(e.getId(), e.getName(), e.getEmail(), e.getDepartmentId(), d.name());
	}
}