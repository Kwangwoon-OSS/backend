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
    private Long department_id;
    private Long professor_id;
    private Long semester_id;

    public SubjectResponse(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.code = subject.getCode();
        this.department_id = subject.getDepartment().getId();
        this.professor_id = subject.getProfessor().getId();
        this.semester_id = subject.getSemester().getId();
    }
}
