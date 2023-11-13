package com.example.be_kwangwoon.domain.user.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class EmailVerifyResponse {

    private final Boolean success;

    @Builder
    public EmailVerifyResponse(Boolean success) {
        this.success = success;
    }


    public static EmailVerifyResponse from(boolean success) {
        return EmailVerifyResponse.builder()
                .success(success)
                .build();
    }
}
