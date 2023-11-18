package com.example.be_kwangwoon.domain.subject.service;

import com.example.be_kwangwoon.domain.semester.repository.SemesterRepository;
import com.example.be_kwangwoon.domain.subject.dto.SubjectResponse;
import com.example.be_kwangwoon.domain.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<SubjectResponse> findAllSubjectBySemester(Long semesterId) {
        return subjectRepository.findBySemester_id(semesterId)
                .stream()
                .map(SubjectResponse::new)
                .toList();
    }

}
