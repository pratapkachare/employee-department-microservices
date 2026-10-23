package com.oneenterprise.department.repository;
import com.oneenterprise.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DepartmentRepository extends JpaRepository<Department,Long> { boolean existsByNameIgnoreCase(String name); }