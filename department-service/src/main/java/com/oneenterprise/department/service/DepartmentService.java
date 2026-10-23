package com.oneenterprise.department.service;

import com.oneenterprise.department.dto.*;
import com.oneenterprise.department.entity.Department;
import com.oneenterprise.department.exception.ResourceNotFoundException;
import com.oneenterprise.department.repository.DepartmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepartmentService {
	private final DepartmentRepository repository;

	public DepartmentService(DepartmentRepository repository) {
		this.repository = repository;
	}

	public DepartmentResponse create(DepartmentRequest r) {
		String n = r.name().trim();
		if (repository.existsByNameIgnoreCase(n))
			throw new IllegalArgumentException("Department name already exists: " + n);
		return response(repository.save(new Department(n, r.description())));
	}

	@Transactional(readOnly = true)
	public List<DepartmentResponse> findAll() {
		return repository.findAll().stream().map(this::response).toList();
	}

	@Transactional(readOnly = true)
	public DepartmentResponse findById(Long id) {
		return response(entity(id));
	}

	public DepartmentResponse update(Long id, DepartmentRequest r) {
		Department d = entity(id);
		String n = r.name().trim();
		if (!d.getName().equalsIgnoreCase(n) && repository.existsByNameIgnoreCase(n))
			throw new IllegalArgumentException("Department name already exists: " + n);
		d.setName(n);
		d.setDescription(r.description());
		return response(repository.save(d));
	}

	public void delete(Long id) {
		repository.delete(entity(id));
	}

	private Department entity(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found: " + id));
	}

	private DepartmentResponse response(Department d) {
		return new DepartmentResponse(d.getId(), d.getName(), d.getDescription());
	}
}