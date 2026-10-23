package com.oneenterprise.employee.dto;

import jakarta.validation.constraints.*;

public record EmployeeRequest(@NotBlank @Size(max = 120) String name, @NotBlank @Email @Size(max = 180) String email,
		@NotNull @Positive Long departmentId) {
}