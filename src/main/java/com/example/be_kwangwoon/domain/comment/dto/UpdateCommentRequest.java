package com.example.be_kwangwoon.domain.comment.dto;

import com.example.be_kwangwoon.domain.comment.domain.Comment;
import com.example.be_kwangwoon.domain.comment.domain.Used;
import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCommentRequest {
    private String content;
    private Used used;
}
