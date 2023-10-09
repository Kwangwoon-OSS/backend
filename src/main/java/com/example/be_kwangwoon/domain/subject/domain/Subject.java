package com.example.be_kwangwoon.domain.subject.domain;


import com.example.be_kwangwoon.domain.department.domain.Department;
import com.example.be_kwangwoon.domain.professor.domain.Professor;
import com.example.be_kwangwoon.domain.semester.domain.Semester;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;
    private String name;
    private String code; //학정번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Builder
    public Subject(Long id, String name, String code, Department department, Semester semester, Professor professor) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.department = department;
        this.semester = semester;
        this.professor = professor;
    }
}
