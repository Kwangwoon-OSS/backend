package com.example.be_kwangwoon.domain.post.dto;

import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.domain.Status;
import com.example.be_kwangwoon.domain.post.domain.Type;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Status status;
    private Type type;
    private LocalDateTime deadline;
    private int views;
    private String contact;
    private int people;
    private Long user_id;
    private Long subject_id;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.status = post.getStatus();
        this.type = post.getType();
        this.deadline = post.getDeadline();
        this.views = post.getViews();
        this.contact = post.getContact();
        this.people = post.getPeople();
        this.user_id = post.getUser().getId();
        this.subject_id = post.getSubject().getId();
        this.createAt = post.getCreateAt();
        this.updateAt = post.getUpdateAt();
    }
}
