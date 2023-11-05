package com.example.be_kwangwoon.domain.department.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;
    private String name;

    @Builder
    public Department(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Builder
    public Department(String name) {
        this.name = name;
    }
}
