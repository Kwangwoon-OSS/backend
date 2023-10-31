package com.example.be_kwangwoon.domain.user.domain.request;


import com.example.be_kwangwoon.domain.user.domain.Certification;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignUpRequest {

    private final String email;
    private final String password;
    private final String passwordConfirm;
    private final Certification isCertification;

    @Builder
    public UserSignUpRequest(String email, String password, String passwordConfirm, Certification isCertification) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.isCertification = isCertification;
    }
}
