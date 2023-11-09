package com.example.be_kwangwoon.domain.department.dto;

import com.example.be_kwangwoon.domain.department.domain.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DepartmentResponse {
    private Long id;
    private String name;

    public DepartmentResponse(Department department){
        this.id = department.getId();
        this.name = department.getName();
    }
}
