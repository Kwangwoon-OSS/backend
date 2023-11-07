package com.example.be_kwangwoon.domain.user.controller.response;

import com.example.be_kwangwoon.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileResponse {
    private final String nickname;
    private final String departmentName;
    private final String introduction;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .nickname(user.getNickname())
                .departmentName(user.getDepartment().getName())
                .introduction(user.getIntroduction())
                .build();

    }

    @Builder
    public UserProfileResponse(String nickname, String departmentName, String introduction) {
        this.nickname = nickname;
        this.departmentName = departmentName;
        this.introduction = introduction;
    }
}
