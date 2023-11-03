package com.example.be_kwangwoon.domain.post.service;

import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.dto.AddPostRequest;
import com.example.be_kwangwoon.domain.post.repository.PostRepository;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    public Post save(AddPostRequest request, User user, Subject subject){
        return postRepository.save(request.toEntity(user, subject));
    }
}
