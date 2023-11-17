package com.example.be_kwangwoon.domain.post.controller;

import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.dto.AddPostRequest;
import com.example.be_kwangwoon.domain.post.dto.FindPostBySubjectRequest;
import com.example.be_kwangwoon.domain.post.dto.PostResponse;
import com.example.be_kwangwoon.domain.post.dto.UpdatePostRequest;
import com.example.be_kwangwoon.domain.post.service.PostService;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.subject.dto.SubjectResponse;
import com.example.be_kwangwoon.domain.user.domain.User;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Tag(name = "Post", description = "계시판 API")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @CrossOrigin
    @PostMapping("/posts")
    public ResponseEntity<Void> addPost(@RequestBody AddPostRequest request, @AuthenticationPrincipal User user) {
        postService.addPost(request, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @CrossOrigin
    @PutMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable long id,
                                           @RequestBody UpdatePostRequest request, @AuthenticationPrincipal User user) {
        postService.updatePost(id, request, user);

        return ResponseEntity.ok()
                .build();
    }

    @CrossOrigin
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> findPost(@PathVariable long id) {
        Post post = postService.findPost(id);
        return ResponseEntity.ok()
                .body(new PostResponse(post));
    }

    @CrossOrigin
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> findAllPost() {
        List<PostResponse> posts = postService.findAllPost()
                .stream()
                .map(PostResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(posts);
    }

    @CrossOrigin
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id, @AuthenticationPrincipal User user) {
        postService.deletePost(id, user);
        return ResponseEntity.ok()
                .build();
    }

    @CrossOrigin
    @GetMapping("/posts/newposts")
    public ResponseEntity<List<PostResponse>> findNewPost() {
        List<PostResponse> post = postService.findNewPost()
                .stream()
                .map(PostResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(post);
    }

    @CrossOrigin
    @GetMapping("/posts/filter/{semesterId}")
    public ResponseEntity<List<PostResponse>> findAllPostBySemester(@PathVariable Long semesterId) {
        List<PostResponse> list = postService.findAllPostBySemester(semesterId);
        return ResponseEntity.ok()
                .body(list);
    }

    @CrossOrigin
    @GetMapping("/posts/filter2/{departmentId}")
    public ResponseEntity<List<PostResponse>> findAllPostByDepartment(@PathVariable Long departmentId) {
        List<PostResponse> list = postService.findAllPostByDepartment(departmentId);
        return ResponseEntity.ok()
                .body(list);
    }


    @CrossOrigin
    @GetMapping("/posts/filter3/{subjectName}")
    public ResponseEntity<List<PostResponse>> findAllPostBySubject(@PathVariable String subjectName) {
        List<PostResponse> list = postService.findAllPostBySubject(subjectName);
        return ResponseEntity.ok()
                .body(list);
    }


    /*
    @CrossOrigin
    @PostMapping("/posts/filter3")
    public ResponseEntity<List<PostResponse>> findAllPostBySubject(@RequestBody FindPostBySubjectRequest findPostBySubjectRequest) {
        List<PostResponse> list = postService.findAllPostBySubject(findPostBySubjectRequest.getName());
        return ResponseEntity.ok()
                .body(list);
    }
     */
}
