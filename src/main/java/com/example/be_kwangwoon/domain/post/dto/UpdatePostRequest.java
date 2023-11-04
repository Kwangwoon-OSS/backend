package com.example.be_kwangwoon.domain.post.dto;

import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdatePostRequest {

    private String title;
    private String content;
    private Status status;
    private LocalDateTime deadline;
    private String contact;
    private int people;
    private Long subjectId;
}
