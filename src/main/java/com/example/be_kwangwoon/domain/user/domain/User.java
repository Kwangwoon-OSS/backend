package com.example.be_kwangwoon.domain.user.domain;

import com.example.be_kwangwoon.domain.department.domain.Department;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Used used;
    private Certification isCertification;
    private String introduction;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    @Builder
    public User(Long id, String email, String password, String nickname, Used used, Certification isCertification, String introduction, LocalDateTime createAt, Department department) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.used = used;
        this.isCertification = isCertification;
        this.introduction = introduction;
        this.createAt = createAt;
        this.department = department;
    }
}
