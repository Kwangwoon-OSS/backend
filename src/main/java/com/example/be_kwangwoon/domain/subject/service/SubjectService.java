package com.example.be_kwangwoon.domain.subject.service;

import com.example.be_kwangwoon.domain.subject.dto.SubjectResponse;
import com.example.be_kwangwoon.domain.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public List<SubjectResponse> findAllSubject() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectResponse::new)
                .toList();
    }
}
