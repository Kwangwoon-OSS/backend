package com.example.be_kwangwoon.domain.semester.service;

import com.example.be_kwangwoon.domain.semester.domain.Semester;
import com.example.be_kwangwoon.domain.semester.dto.SemesterResponse;
import com.example.be_kwangwoon.domain.semester.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@RequiredArgsConstructor
@Service
public class SemesterService {
    private final SemesterRepository semesterRepository;

    public List<SemesterResponse> findAllSemester() {
        List<Semester> list = semesterRepository.findAll();
        return list.stream()
                .map(SemesterResponse::new)
                .toList();
    }
}
