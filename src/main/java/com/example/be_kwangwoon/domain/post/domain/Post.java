package com.example.be_kwangwoon.domain.post.domain;

import com.example.be_kwangwoon.domain.post.dto.UpdatePostRequest;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String content;
    private Status status;
    private Type type;
    private LocalDateTime deadline;
    private int views;
    private String contact;
    private int people;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @Builder
    public Post(String title, String content, Status status, Type type, LocalDateTime deadline, int views, String contact, int people, User user, Subject subject) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.type = type;
        this.deadline = deadline;
        this.views = views;
        this.contact = contact;
        this.people = people;
        this.user = user;
        this.subject = subject;
    }

    public void updatePost(UpdatePostRequest request, Subject subject) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.status = request.getStatus();
        this.deadline = request.getDeadline();
        this.contact = request.getContact();
        this.people = request.getPeople();
        this.subject = subject;
    }

    public void updatePostviews() {
        this.views += 1;
    }
}
