package com.oneenterprise.employee.controller;

import com.oneenterprise.employee.dto.*;
import com.oneenterprise.employee.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	private final EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmployeeResponse create(@Valid @RequestBody EmployeeRequest r) {
		return service.create(r);
	}

	@GetMapping
	public List<EmployeeResponse> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public EmployeeResponse findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PutMapping("/{id}")
	public EmployeeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest r) {
		return service.update(id, r);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}