package com.example.be_kwangwoon.domain.comment.dto;

import com.example.be_kwangwoon.domain.comment.domain.Comment;
import com.example.be_kwangwoon.domain.comment.domain.Used;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private Used used;
    private String username;
    private List<Long> childsIds;
    private LocalDateTime createdtime;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.used = comment.getUsed();
        this.username = comment.getUser().getNickname();
        this.childsIds = new ArrayList<>();
        this.createdtime = comment.getCreateAt();
        for(Comment c : comment.getChildComments())
            this.childsIds.add(c.getId());
    }
}
