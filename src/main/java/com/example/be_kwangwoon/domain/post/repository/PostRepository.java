package com.example.be_kwangwoon.domain.post.repository;

import com.example.be_kwangwoon.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
