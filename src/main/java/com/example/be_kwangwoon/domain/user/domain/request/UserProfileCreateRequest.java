package com.example.be_kwangwoon.domain.user.domain.request;


import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileCreateRequest {
    private final Long departmentId;
    private final String nickname;
    private final String introduction;

    @Builder
    public UserProfileCreateRequest(Long departmentId, String nickname, String introduction) {
        this.departmentId = departmentId;
        this.nickname = nickname;
        this.introduction = introduction;
    }
}
