package com.example.be_kwangwoon.domain.subject.controller;

import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.subject.dto.FindSubjectBySemesterRequest;
import com.example.be_kwangwoon.domain.subject.dto.SubjectResponse;
import com.example.be_kwangwoon.domain.subject.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @CrossOrigin
    @GetMapping("/subject")
    public ResponseEntity<List<SubjectResponse>> findAllSubject() {
        List<SubjectResponse> list = subjectService.findAllSubject();
        return ResponseEntity.ok()
                .body(list);
    }

    @CrossOrigin
    @GetMapping("/subjectbysemester")
    public ResponseEntity<List<SubjectResponse>> findAllSubjectBySemester(@RequestBody FindSubjectBySemesterRequest findSubjectBySemesterRequest) {
        List<SubjectResponse> list = subjectService.findAllSubjectBySemester(findSubjectBySemesterRequest);
        return ResponseEntity.ok()
                .body(list);
    }
}
