package com.example.be_kwangwoon.domain.user.controller;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    private final String email;
    private final String password;

    @Builder
    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
