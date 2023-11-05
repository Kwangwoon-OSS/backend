package com.example.be_kwangwoon.domain.post.controller;

import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.dto.AddPostRequest;
import com.example.be_kwangwoon.domain.post.dto.PostResponse;
import com.example.be_kwangwoon.domain.post.dto.UpdatePostRequest;
import com.example.be_kwangwoon.domain.post.service.PostService;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.user.domain.User;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Post", description = "계시판 API")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<Post> addPost(@RequestBody AddPostRequest request, @AuthenticationPrincipal User user) {
        Post addedPost = postService.addPost(request, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addedPost);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id,
                                           @RequestBody UpdatePostRequest request) {
        Post updatedPost = postService.updatePost(id, request);

        return ResponseEntity.ok()
                .body(updatedPost);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> findPost(@PathVariable long id) {
        Post post = postService.findPost(id);
        return ResponseEntity.ok()
                .body(new PostResponse(post));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> findAllPost() {
        List<PostResponse> posts = postService.findAllPost()
                .stream()
                .map(PostResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(posts);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/posts/newposts")
    public ResponseEntity<List<PostResponse>> findNewPost() {
        List<PostResponse> post = postService.findNewPost()
                .stream()
                .map(PostResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(post);
    }

}
