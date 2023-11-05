package com.example.be_kwangwoon.domain.bookmark.repository;

import com.example.be_kwangwoon.domain.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUser_id(Long userId);
}
