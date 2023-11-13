package com.example.be_kwangwoon.domain.user.domain.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailCheckRequest {

    private final String email;
    private final String univName;

    @Builder
    public EmailCheckRequest(String email, String univName) {
        this.email = email;
        this.univName = univName;
    }
}
