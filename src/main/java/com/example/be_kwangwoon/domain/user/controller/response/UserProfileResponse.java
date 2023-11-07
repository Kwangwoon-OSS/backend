package com.example.be_kwangwoon.domain.user.controller.response;

import com.example.be_kwangwoon.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileResponse {
    private final String nickname;
    private final String email;
    private final String departmentName;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .departmentName(user.getDepartment().getName())
                .build();
    }

    @Builder
    public UserProfileResponse(String nickname, String email, String departmentName) {
        this.nickname = nickname;
        this.email = email;
        this.departmentName = departmentName;
    }
}
