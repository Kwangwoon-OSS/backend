package com.example.be_kwangwoon.domain.post.service;

import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.dto.AddPostRequest;
import com.example.be_kwangwoon.domain.post.dto.UpdatePostRequest;
import com.example.be_kwangwoon.domain.post.repository.PostRepository;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.subject.repository.SubjectRepository;
import com.example.be_kwangwoon.domain.user.domain.User;
import com.example.be_kwangwoon.domain.user.repository.UserRepository;
import com.example.be_kwangwoon.global.common.exception.CustomException;
import com.example.be_kwangwoon.global.common.exception.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;


    @Transactional
    public void addPost(AddPostRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));
        Subject subject = subjectRepository.findById(request.getSubjectId()).orElseThrow(() -> new IllegalArgumentException("not found : " + request.getSubjectId()));
        postRepository.save(request.toEntity(user, subject));
    }

    @Transactional
    public void updatePost(long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        authorizeAuthor(post, id);
        Subject subject = subjectRepository.findById(request.getSubjectId()).orElseThrow(() -> new IllegalArgumentException("not found : " + request.getSubjectId()));
        post.updatePost(request, subject);
    }

    @Transactional
    public void deletePost(long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        authorizeAuthor(post, user.getId());
        postRepository.deleteById(id);
    }

    public Post findPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        return post;
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }


    public List<Post> findNewPost() {
        List<Post> list = postRepository.findAll(PageRequest.of(0, 2, Sort.by("createAt").descending())).
                stream().
                toList();
        return list;
    }

    private static void authorizeAuthor(Post post, long userid) {
        long puserId = post.getUser().getId();
        if (puserId != userid) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
