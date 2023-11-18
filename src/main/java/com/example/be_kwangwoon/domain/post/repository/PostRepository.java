package com.example.be_kwangwoon.domain.post.repository;

import com.example.be_kwangwoon.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findBySubject_id(Long subjectId);
}
