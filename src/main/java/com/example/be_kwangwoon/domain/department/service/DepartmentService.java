package com.example.be_kwangwoon.domain.department.service;

import com.example.be_kwangwoon.domain.department.domain.Department;
import com.example.be_kwangwoon.domain.department.dto.DepartmentResponse;
import com.example.be_kwangwoon.domain.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<DepartmentResponse> findAllDepartment() {
        List<Department> list = departmentRepository.findAll();
        return list.stream()
                .map(DepartmentResponse::new)
                .toList();
    }
}
