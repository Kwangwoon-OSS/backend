package com.example.be_kwangwoon.domain.user.domain.request;


import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailVerifyRequest {


    private final String email;
    private final String univName;
    private final int code;

    @Builder
    public EmailVerifyRequest(String email, String univName, int code) {
        this.email = email;
        this.univName = univName;
        this.code = code;
    }
}
