package com.example.be_kwangwoon.domain.post.controller;

import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.dto.AddPostRequest;
import com.example.be_kwangwoon.domain.post.service.PostService;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.user.domain.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping("/posts")
    public ResponseEntity<Void> addPost(@RequestBody AddPostRequest request, @AuthenticationPrincipal User user) {
        Subject subject;
        Post addedPost = postService.save(request, user, subject);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

}
