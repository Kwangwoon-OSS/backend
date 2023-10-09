package com.example.be_kwangwoon.domain.professor.domain;


import com.example.be_kwangwoon.domain.department.domain.Department;
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
@Table(name = "professors")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Long id;

    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @Builder
    public Professor(Long id, String name, Department department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
}
