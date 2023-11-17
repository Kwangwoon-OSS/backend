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

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "게시글 등록하기")
    @PostMapping("/posts")
    public ResponseEntity<Void> addPost(@RequestBody AddPostRequest request, @AuthenticationPrincipal User user) {
        postService.addPost(request, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @CrossOrigin
    @Operation(summary = "게시글 수정하기")
    @PutMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable long id,
                                           @RequestBody UpdatePostRequest request, @AuthenticationPrincipal User user) {
        postService.updatePost(id, request, user);

        return ResponseEntity.ok()
                .build();
    }

    @CrossOrigin
    @Operation(summary = "게시글 상세보기")
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> findPost(@PathVariable long id) {
        Post post = postService.findPost(id);
        return ResponseEntity.ok()
                .body(new PostResponse(post));
    }

    @CrossOrigin
    @Operation(summary = "모든 게시글 리스트 보기")
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
    @Operation(summary = "특정 게시글 삭제하기")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id, @AuthenticationPrincipal User user) {
        postService.deletePost(id, user);
        return ResponseEntity.ok()
                .build();
    }

    @CrossOrigin
    @Operation(summary = "최근에 올라온 게시글 리스트 가져오기")
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
    @Operation(summary = "학기별로 과목 목록 가져오기")
    @GetMapping("/posts/filter/{semesterId}")
    public ResponseEntity<List<PostResponse>> findAllPostBySemester(@PathVariable Long semesterId) {
        List<PostResponse> list = postService.findAllPostBySemester(semesterId);
        return ResponseEntity.ok()
                .body(list);
    }

    @CrossOrigin
    @Operation(summary = "학과별로 과목 목록 가져오기")
    @GetMapping("/posts/filter2/{departmentId}")
    public ResponseEntity<List<PostResponse>> findAllPostByDepartment(@PathVariable Long departmentId) {
        List<PostResponse> list = postService.findAllPostByDepartment(departmentId);
        return ResponseEntity.ok()
                .body(list);
    }


    @CrossOrigin
    @Operation(summary = "과목 이름별로 과목 목록 가져오기(미작동)")
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
