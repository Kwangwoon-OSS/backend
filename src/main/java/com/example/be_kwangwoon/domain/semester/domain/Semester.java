package com.example.be_kwangwoon.domain.semester.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "semesters")
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "semester_id")
    private Long id;

    private String years;

    private String semester;

    @Builder
    public Semester(Long id, String years, String semester) {
        this.id = id;
        this.years = years;
        this.semester = semester;
    }
}
