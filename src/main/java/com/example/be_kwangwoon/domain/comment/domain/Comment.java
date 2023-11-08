package com.example.be_kwangwoon.domain.comment.domain;


import com.example.be_kwangwoon.domain.comment.dto.UpdateCommentRequest;
import com.example.be_kwangwoon.domain.post.domain.Post;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childComments = new ArrayList<>();

    private Used used;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @Builder
    public Comment(String content, Comment parentComment, User user, Post post, Used used) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.parentComment = parentComment;
        this.used = used;
    }

    public void updateComment(UpdateCommentRequest request) {
        this.content = request.getContent();
        this.used = request.getUsed();
    }

    public void updateChild(Comment comment) {
        childComments.add(comment);
    }
}
