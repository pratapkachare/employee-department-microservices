package com.oneenterprise.department.controller;

import com.oneenterprise.department.dto.*;
import com.oneenterprise.department.service.DepartmentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
	private final DepartmentService service;

	public DepartmentController(DepartmentService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DepartmentResponse create(@Valid @RequestBody DepartmentRequest r) {
		return service.create(r);
	}

	@GetMapping
	public List<DepartmentResponse> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public DepartmentResponse findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PutMapping("/{id}")
	public DepartmentResponse update(@PathVariable Long id, @Valid @RequestBody DepartmentRequest r) {
		return service.update(id, r);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}