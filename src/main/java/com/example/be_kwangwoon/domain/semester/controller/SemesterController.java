package com.example.be_kwangwoon.domain.semester.controller;


import com.amazonaws.Response;
import com.example.be_kwangwoon.domain.semester.dto.SemesterResponse;
import com.example.be_kwangwoon.domain.semester.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class SemesterController {
    private final SemesterService semesterService;

    @CrossOrigin
    @GetMapping("/semester")
    public ResponseEntity<List<SemesterResponse>>  findAllSemester() {
        List<SemesterResponse> list = semesterService.findAllSemester();
        return ResponseEntity.ok()
                .body(list);
    }
}
