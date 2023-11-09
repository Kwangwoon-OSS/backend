package com.example.be_kwangwoon.domain.comment.Controller;


import com.example.be_kwangwoon.domain.comment.domain.Comment;
import com.example.be_kwangwoon.domain.comment.dto.AddCommentRequest;
import com.example.be_kwangwoon.domain.comment.dto.CommentResponse;
import com.example.be_kwangwoon.domain.comment.dto.UpdateCommentRequest;
import com.example.be_kwangwoon.domain.comment.service.CommentService;
import com.example.be_kwangwoon.domain.post.dto.UpdatePostRequest;
import com.example.be_kwangwoon.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<Void> addComment(@AuthenticationPrincipal User user, @RequestBody AddCommentRequest request, @PathVariable long postId) {
        commentService.addComment(user, request, postId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/posts/{postId}/{commentId}")
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal User user, @RequestBody UpdateCommentRequest updateCommentRequest, @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.updateComment(user, updateCommentRequest, postId, commentId);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/posts/{postId}/comment")
    public ResponseEntity<List<CommentResponse>> findAllComment(@PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(commentService.findAllComment(postId));
    }

    @DeleteMapping("/posts/{postId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal User user, @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(user, commentId);
        return ResponseEntity.ok()
                .build();
    }
}
