package com.example.be_kwangwoon.domain.department.repository;

import com.example.be_kwangwoon.domain.department.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
