package com.oneenterprise.employee.client;

import com.oneenterprise.employee.dto.DepartmentSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "department-service")
public interface DepartmentClient {
	
	@GetMapping("/api/departments/{id}")
	DepartmentSummary getDepartmentById(@PathVariable("id") Long id);
}