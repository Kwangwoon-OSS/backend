package com.example.be_kwangwoon.domain.semester.repository;

import com.example.be_kwangwoon.domain.comment.domain.Comment;
import com.example.be_kwangwoon.domain.semester.domain.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
    Semester findByYearsAndSemester(String years, String semester);
}
