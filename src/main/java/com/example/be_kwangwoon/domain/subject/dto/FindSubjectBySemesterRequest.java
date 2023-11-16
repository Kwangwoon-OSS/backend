package com.example.be_kwangwoon.domain.subject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FindSubjectBySemesterRequest {
    private Long years;
    private Long semester;
}
