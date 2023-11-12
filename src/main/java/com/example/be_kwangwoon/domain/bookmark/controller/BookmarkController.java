package com.example.be_kwangwoon.domain.bookmark.controller;

import com.example.be_kwangwoon.domain.bookmark.domain.Bookmark;
import com.example.be_kwangwoon.domain.bookmark.service.BookmarkService;
import com.example.be_kwangwoon.domain.post.dto.PostResponse;
import com.example.be_kwangwoon.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @PostMapping("/posts/{postId}/interest")
    public ResponseEntity<Void> addInterestPost(@AuthenticationPrincipal User user, @PathVariable Long postId){
        bookmarkService.addInterestPost(user, postId);
        return ResponseEntity.ok()
                .build();
    }

    @CrossOrigin
    @DeleteMapping("/posts/{postId}/interest")
    public ResponseEntity<Void> deleteInterestPost(@AuthenticationPrincipal User user, @PathVariable Long postId){
        bookmarkService.deleteInterestPost(user, postId);
        return ResponseEntity.ok()
                .build();
    }
}
