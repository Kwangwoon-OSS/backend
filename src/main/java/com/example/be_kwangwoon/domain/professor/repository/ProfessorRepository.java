package com.example.be_kwangwoon.domain.professor.repository;

import com.example.be_kwangwoon.domain.professor.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
