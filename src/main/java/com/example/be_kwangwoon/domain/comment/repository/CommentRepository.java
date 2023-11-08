package com.example.be_kwangwoon.domain.comment.repository;

import com.example.be_kwangwoon.domain.bookmark.domain.Bookmark;
import com.example.be_kwangwoon.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_id(Long postId);
}
