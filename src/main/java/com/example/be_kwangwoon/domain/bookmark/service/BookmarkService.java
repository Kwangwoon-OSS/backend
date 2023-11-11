package com.example.be_kwangwoon.domain.bookmark.service;

import com.example.be_kwangwoon.domain.bookmark.domain.Bookmark;
import com.example.be_kwangwoon.domain.bookmark.repository.BookmarkRepository;
import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.repository.PostRepository;
import com.example.be_kwangwoon.domain.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;

    @Transactional
    public List<Post> findInterestPost(User user) {
        List<Bookmark> list = bookmarkRepository.findByUser_id(user.getId());
        List<Post> plist = new ArrayList<>();
        for (Bookmark bookmark : list)
            plist.add(postRepository.findById(bookmark.getPost().getId()).orElseThrow(() -> new IllegalArgumentException("not found : " + bookmark.getPost().getId())));
        return plist;
    }

}
