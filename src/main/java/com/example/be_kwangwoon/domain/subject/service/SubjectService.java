package com.example.be_kwangwoon.domain.subject.service;

import com.example.be_kwangwoon.domain.semester.domain.Semester;
import com.example.be_kwangwoon.domain.semester.repository.SemesterRepository;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.subject.dto.FindSubjectBySemesterRequest;
import com.example.be_kwangwoon.domain.subject.dto.SubjectResponse;
import com.example.be_kwangwoon.domain.subject.repository.SubjectRepository;
import com.example.be_kwangwoon.global.common.exception.CustomException;
import com.example.be_kwangwoon.global.common.exception.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;

    public List<SubjectResponse> findAllSubject() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectResponse::new)
                .toList();
    }

    @Transactional
    public List<SubjectResponse> findAllSubjectBySemester(FindSubjectBySemesterRequest request) {
        List<Subject> slist = subjectRepository.findBySemester_id(
                semesterRepository
                        .findByYearsAndSemester(request.getYears() + "", request.getSemester() + "")
                        .getId());

        return slist
                .stream()
                .map(SubjectResponse::new)
                .toList();
    }
}
