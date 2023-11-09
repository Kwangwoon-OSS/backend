package com.example.be_kwangwoon.domain.comment.dto;

import com.example.be_kwangwoon.domain.comment.domain.Comment;
import com.example.be_kwangwoon.domain.comment.domain.Used;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private Used used;
    private List<Long> childsIds;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.used = comment.getUsed();
        this.childsIds = new ArrayList<>();
        for(Comment c : comment.getChildComments())
            this.childsIds.add(c.getId());
    }
}
