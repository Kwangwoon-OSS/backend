package com.example.be_kwangwoon.domain.user.domain;

import com.example.be_kwangwoon.domain.department.domain.Department;
import com.example.be_kwangwoon.domain.user.domain.request.UserProfileCreateRequest;
import com.example.be_kwangwoon.domain.user.domain.request.UserProfileUpdateRequest;
import com.example.be_kwangwoon.domain.user.domain.request.UserSignUpRequest;
import com.example.be_kwangwoon.global.common.exception.CustomException;
import com.example.be_kwangwoon.global.common.exception.ExceptionCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User implements UserDetails {
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

    public static User from(UserSignUpRequest userSignUpRequest, BCryptPasswordEncoder bCryptPasswordEncoder) {
        if (!userSignUpRequest.getPassword().equals(userSignUpRequest.getPasswordConfirm())) {
            throw new CustomException(ExceptionCode.PASSWORD_NOT_MATCH);
        }
        return User.builder()
                .email(userSignUpRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(userSignUpRequest.getPassword()))
                .isCertification(userSignUpRequest.getIsCertification())
                .build();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void createProfile(UserProfileCreateRequest userProfileCreateRequest, Department department) {
        this.nickname = userProfileCreateRequest.getNickname();
        this.introduction = userProfileCreateRequest.getIntroduction();
        this.department = department;
    }

    public void updateProfile(UserProfileUpdateRequest userProfileUpdateRequest, Department department) {
        this.nickname = userProfileUpdateRequest.getNickname();
        this.introduction = userProfileUpdateRequest.getIntroduction();
        this.department = department;
    }
}
