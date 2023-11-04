package com.example.be_kwangwoon.domain.subject.repository;

import com.example.be_kwangwoon.domain.subject.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
