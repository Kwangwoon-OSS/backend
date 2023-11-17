package com.example.be_kwangwoon.domain.post.dto;

import com.example.be_kwangwoon.domain.subject.domain.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FindPostBySubjectRequest {
    private String name;
}
