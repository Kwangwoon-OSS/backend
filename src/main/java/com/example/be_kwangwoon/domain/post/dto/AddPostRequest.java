package com.example.be_kwangwoon.domain.post.dto;


import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.domain.Status;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPostRequest {

    private Long id;
    private String title;
    private String content;
    private Status status;
    private LocalDateTime deadline;
    private int views;
    private String contact;
    private int people;

    public Post toEntity(User user, Subject subject) {
        return Post.builder()
                .title(title)
                .content(content)
                .status(status)
                .deadline(deadline)
                .views(views)
                .contact(contact)
                .people(people)
                .user(user)
                .subject(subject)
                .build();
    }
}
