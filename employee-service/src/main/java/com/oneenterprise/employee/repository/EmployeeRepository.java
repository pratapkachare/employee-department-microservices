package com.oneenterprise.employee.repository;

import com.oneenterprise.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	boolean existsByEmailIgnoreCase(String email);
}