package com.oneenterprise.employee.dto;

public record EmployeeResponse(Long id, String name, String email, Long departmentId, String departmentName) {
}