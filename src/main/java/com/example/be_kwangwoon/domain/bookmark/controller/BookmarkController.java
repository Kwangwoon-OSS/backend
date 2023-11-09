package com.example.be_kwangwoon.domain.bookmark.controller;

import com.example.be_kwangwoon.domain.bookmark.domain.Bookmark;
import com.example.be_kwangwoon.domain.bookmark.service.BookmarkService;
import com.example.be_kwangwoon.domain.post.dto.PostResponse;
import com.example.be_kwangwoon.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @CrossOrigin
    @GetMapping("/posts/interestin")
    public ResponseEntity<List<PostResponse>> findInterestPost(@AuthenticationPrincipal User user) {
        List<PostResponse> list = bookmarkService.findInterestPost(user)
                .stream()
                .map(PostResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(list);
    }
}
