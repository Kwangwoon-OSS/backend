package com.example.be_kwangwoon.domain.post.service;

import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.dto.AddPostRequest;
import com.example.be_kwangwoon.domain.post.dto.UpdatePostRequest;
import com.example.be_kwangwoon.domain.post.repository.PostRepository;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.subject.repository.SubjectRepository;
import com.example.be_kwangwoon.domain.user.domain.User;
import com.example.be_kwangwoon.global.common.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.be_kwangwoon.global.common.exception.ExceptionCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final SubjectRepository subjectRepository;

    public Post addPost(AddPostRequest request, User user) {
        Subject subject = subjectRepository.findById(request.getSubjectId()).orElseThrow(() -> new IllegalArgumentException("not found : " + request.getSubjectId()));
        return postRepository.save(request.toEntity(user, subject));
    }


    public Post findPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        return post;
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        authorizeAuthor(post, id);
        postRepository.deleteById(id);
    }

    @Transactional
    public Post updatePost(long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        authorizeAuthor(post, id);
        Subject subject = subjectRepository.findById(request.getSubjectId()).orElseThrow(() -> new IllegalArgumentException("not found : " + request.getSubjectId()));
        post.updatePost(request, subject);

        return post;
    }

    private static void authorizeAuthor(Post post, long id) {
        long userId = post.getUser().getId();
        if (userId != id) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
