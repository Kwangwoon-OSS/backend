package com.example.be_kwangwoon.domain.comment.Controller;


import com.example.be_kwangwoon.domain.comment.domain.Comment;
import com.example.be_kwangwoon.domain.comment.dto.AddCommentRequest;
import com.example.be_kwangwoon.domain.comment.dto.CommentResponse;
import com.example.be_kwangwoon.domain.comment.dto.UpdateCommentRequest;
import com.example.be_kwangwoon.domain.comment.service.CommentService;
import com.example.be_kwangwoon.domain.post.dto.UpdatePostRequest;
import com.example.be_kwangwoon.domain.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Comment", description = "계시판 댓글 API")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @CrossOrigin
    @Operation(summary = "게시글 댓글 달기")
    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<Void> addComment(@AuthenticationPrincipal User user, @RequestBody AddCommentRequest request, @PathVariable long postId) {
        commentService.addComment(user, request, postId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @CrossOrigin
    @Operation(summary = "게시글 댓글 수정하기")
    @PutMapping("/posts/{postId}/{commentId}")
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal User user, @RequestBody UpdateCommentRequest updateCommentRequest, @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.updateComment(user, updateCommentRequest, postId, commentId);
        return ResponseEntity.ok()
                .build();
    }

    @CrossOrigin
    @Operation(summary = "게시글 댓글 리스트 보기")
    @GetMapping("/posts/{postId}/comment")
    public ResponseEntity<List<CommentResponse>> findAllComment(@PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(commentService.findAllComment(postId));
    }

    @CrossOrigin
    @Operation(summary = "게시글 댓글 삭제하기")
    @DeleteMapping("/posts/{postId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal User user, @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(user, commentId);
        return ResponseEntity.ok()
                .build();
    }
}
