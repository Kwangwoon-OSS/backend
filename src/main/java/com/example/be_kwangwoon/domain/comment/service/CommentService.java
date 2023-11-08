package com.example.be_kwangwoon.domain.comment.service;

import com.example.be_kwangwoon.domain.comment.domain.Comment;
import com.example.be_kwangwoon.domain.comment.dto.AddCommentRequest;
import com.example.be_kwangwoon.domain.comment.dto.CommentResponse;
import com.example.be_kwangwoon.domain.comment.dto.UpdateCommentRequest;
import com.example.be_kwangwoon.domain.comment.repository.CommentRepository;
import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.repository.PostRepository;
import com.example.be_kwangwoon.domain.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void addComment(User user, AddCommentRequest request, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("not found : " + postId));
        Comment parentComment = null;
        if (request.getParentId() != 0)
            parentComment = commentRepository.findById(request.getParentId()).orElseThrow(() -> new IllegalArgumentException("not found : " + request.getParentId()));
        Comment addedComment = commentRepository.save(request.toEntity(parentComment, user, post));
        if (request.getParentId() != 0)
            parentComment.updateChild(addedComment);
    }

    @Transactional
    public void updateComment(User user, UpdateCommentRequest request, Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("not found : " + commentId));
        authorizeAuthor(comment, user.getId());
        comment.updateComment(request);
    }

    public List<CommentResponse> findAllComment(Long postId) {
        List<Comment> list = commentRepository.findByPost_id(postId);
        return list.stream()
                .map(CommentResponse::new)
                .toList();
    }

    private static void authorizeAuthor(Comment comment, long userid) {
        long commentId = comment.getUser().getId();
        if (commentId != userid) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
