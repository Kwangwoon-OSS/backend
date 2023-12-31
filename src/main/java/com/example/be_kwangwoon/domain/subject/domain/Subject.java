package com.example.be_kwangwoon.domain.subject.domain;


import com.example.be_kwangwoon.domain.department.domain.Department;
import com.example.be_kwangwoon.domain.professor.domain.Professor;
import com.example.be_kwangwoon.domain.semester.domain.Semester;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;
    private String name;
    private String code; //학정번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    private Semester semester;
    @Builder
    public Subject(String name, String code, Department department, Semester semester, Professor professor) {
        this.name = name;
        this.code = code;
        this.department = department;
        this.semester = semester;
        this.professor = professor;
    }

    @Builder
    public Subject(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
