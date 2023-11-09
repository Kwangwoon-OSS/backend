package com.example.be_kwangwoon.domain.department.controller;

import com.example.be_kwangwoon.domain.department.dto.DepartmentResponse;
import com.example.be_kwangwoon.domain.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @CrossOrigin
    @GetMapping("/department")
    public ResponseEntity<List<DepartmentResponse>> findAllDepartment() {
        List<DepartmentResponse> list = departmentService.findAllDepartment();
        return ResponseEntity.ok()
                .body(list);
    }
}
