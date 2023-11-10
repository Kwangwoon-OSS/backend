package com.example.be_kwangwoon.domain.subject.dto;

import com.example.be_kwangwoon.domain.subject.domain.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubjectResponse {
    private Long id;
    private String name;
    private String code;
    private String departments_name;
    private String professors_name;
    private String semesters_years;
    private String semesters_semester;
    private Long department_id;
    private Long professor_id;
    private Long semester_id;

    public SubjectResponse(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.code = subject.getCode();
        this.departments_name = subject.getDepartment().getName();
        this.professors_name = subject.getProfessor().getName();
        this.semesters_years = subject.getSemester().getYears();
        this.semesters_semester = subject.getSemester().getSemester();
        this.department_id = subject.getDepartment().getId();
        this.professor_id = subject.getProfessor().getId();
        this.semester_id = subject.getSemester().getId();
    }
}
