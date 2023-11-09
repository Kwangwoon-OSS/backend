package com.example.be_kwangwoon.domain.semester.repository;

import com.example.be_kwangwoon.domain.semester.domain.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
}
