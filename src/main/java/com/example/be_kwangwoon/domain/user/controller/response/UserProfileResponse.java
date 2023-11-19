package com.example.be_kwangwoon.domain.user.controller.response;

import com.example.be_kwangwoon.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileResponse {
    private final Long userId;
    private final String nickname;
    private final String departmentName;
    private final String introduction;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .departmentName(user.getDepartment().getName())
                .introduction(user.getIntroduction())
                .build();

    }

    @Builder
    public UserProfileResponse(Long userId, String nickname, String departmentName, String introduction) {
        this.userId = userId;
        this.nickname = nickname;
        this.departmentName = departmentName;
        this.introduction = introduction;
    }
}
