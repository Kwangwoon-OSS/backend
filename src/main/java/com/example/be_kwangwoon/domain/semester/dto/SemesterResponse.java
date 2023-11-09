package com.example.be_kwangwoon.domain.semester.dto;

import com.example.be_kwangwoon.domain.semester.domain.Semester;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SemesterResponse {

    private Long id;
    private String years;
    private String semester;

    @Builder
    public SemesterResponse(Semester semester) {
        this.id = semester.getId();
        this.years = semester.getYears();
        this.semester = semester.getSemester();
    }
}
