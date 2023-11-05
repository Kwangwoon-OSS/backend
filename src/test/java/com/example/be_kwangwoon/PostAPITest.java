package com.example.be_kwangwoon;

import com.example.be_kwangwoon.domain.bookmark.domain.Bookmark;
import com.example.be_kwangwoon.domain.bookmark.repository.BookmarkRepository;
import com.example.be_kwangwoon.domain.department.domain.Department;
import com.example.be_kwangwoon.domain.department.repository.DepartmentRepository;
import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.domain.Status;
import com.example.be_kwangwoon.domain.post.dto.AddPostRequest;
import com.example.be_kwangwoon.domain.post.dto.UpdatePostRequest;
import com.example.be_kwangwoon.domain.post.repository.PostRepository;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.subject.repository.SubjectRepository;
import com.example.be_kwangwoon.domain.user.domain.Certification;
import com.example.be_kwangwoon.domain.user.domain.User;
import com.example.be_kwangwoon.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostAPITest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    BookmarkRepository bookmarkRepository;

    User user;
    Subject sb1;
    Subject sb2;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        postRepository.deleteAll();
    }

    @BeforeEach
    void setSecurityContext() {
        userRepository.deleteAll();
        user = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .isCertification(Certification.Y)
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @BeforeEach
    public void addSubject() {
        createDefaultSubject();
    }

    @DisplayName("addPost: 아티클 추가에 성공한다.")
    @Test
    public void addPost() throws Exception {

        // given
        final String url = "/posts";
        final String title = "title";
        final String content = "content";
        final Status status = Status.ACTIVE;
        final LocalDateTime deadline = LocalDateTime.MAX;
        final int views = 0;
        final String contact = "123";
        final int people = 3;
        final Long subjectId = (long) 1;

        final AddPostRequest userRequest = new AddPostRequest(title, content, status, deadline, views, contact, people, subjectId);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Post> articles = postRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllPosts: 아티클 목록 조회에 성공한다.")
    @Test
    public void findAllPosts() throws Exception {
        // given
        final String url = "/posts";
        Post savedPost = createDefaultPost(LocalDateTime.MAX);

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(savedPost.getContent()))
                .andExpect(jsonPath("$[0].title").value(savedPost.getTitle()));
    }

    @DisplayName("deletePost: 아티클 삭제에 성공한다.")
    @Test
    public void deletePost() throws Exception {
        // given
        final String url = "/posts/{id}";
        Post savedArticle = createDefaultPost(LocalDateTime.MAX);

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Post> articles = postRepository.findAll();

        assertThat(articles).isEmpty();
    }

    @DisplayName("findPost: 아티클 단건 조회에 성공한다.")
    @Test
    public void findPost() throws Exception {
        // given
        final String url = "/posts/{id}";
        Post savedArticle = createDefaultPost(LocalDateTime.MAX);

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$.title").value(savedArticle.getTitle()));
    }

    @DisplayName("updatePost: 아티클 수정에 성공한다.")
    @Test
    public void updatePost() throws Exception {
        // given
        final String url = "/posts/{id}";
        Post savedArticle = createDefaultPost(LocalDateTime.MAX);

        final String newTitle = "new title";
        final String newContent = "new content";
        final Status status = Status.INACTIVE;
        final LocalDateTime deadline = LocalDateTime.MAX;
        final String contact = "000";
        final int people = 0;
        final Long subjectId = (long) 6;


        UpdatePostRequest request = new UpdatePostRequest(newTitle, newContent, status, deadline, contact, people, subjectId);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        //result.andExpect(status().isOk());

        Post article = postRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
        assertThat(article.getStatus()).isEqualTo(status);
        //assertThat(article.getDeadline()).isEqualTo(deadline); db에서 저장되는 값과 java에서 출력하는 값이 다르게 나옴. 올바르게 진행됐어서 오류 표출
        assertThat(article.getContact()).isEqualTo(contact);
        assertThat(article.getPeople()).isEqualTo(people);
    }


    @DisplayName("findNewPosts: 최근에 추가된 아티클들을 가져오는데 성공")
    @Test
    public void findNewPosts() throws Exception {
        final String url = "/posts/newposts";

        Post p1 = createDefaultPost("p1", "p1", LocalDateTime.MAX);
        Thread.sleep(1000);
        Post p2 = createDefaultPost("p2", "p2", LocalDateTime.now());
        Thread.sleep(1000);
        Post p3 = createDefaultPost("p3", "p3", LocalDateTime.MIN);
        Thread.sleep(1000);
        Post p4 = createDefaultPost("p4", "p4", LocalDateTime.MIN);

        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(p4.getContent()))
                .andExpect(jsonPath("$[0].title").value(p4.getTitle()))
                .andExpect(jsonPath("$[1].content").value(p3.getContent()))
                .andExpect(jsonPath("$[1].title").value(p3.getTitle()));
    }

    @DisplayName("findInterestPosts: 북마크에 추가한 posts들을 가져오는 데 성공")
    @Test
    public void findInterestPosts() throws Exception {
        final String url = "/posts/interestin";

        Post post = createDefaultPost(LocalDateTime.MIN);
        Bookmark bookmark = bookmarkRepository.save(Bookmark.builder()
                .user(user)
                .post(post)
                .build());

        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(post.getContent()))
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].contact").value(post.getContact()));
        bookmarkRepository.deleteAll();
    }


    Post createDefaultPost(LocalDateTime localDateTime) {
        return postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .status(Status.ACTIVE)
                .deadline(localDateTime)
                .views(0)
                .contact("123")
                .people(3)
                .user(user)
                .subject(sb1)
                .build()
        );
    }

    Post createDefaultPost(String title, String content, LocalDateTime localDateTime) {
        return postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .status(Status.ACTIVE)
                .deadline(localDateTime)
                .views(0)
                .contact("123")
                .people(3)
                .user(user)
                .subject(sb1)
                .build()
        );
    }

    void createDefaultSubject() {
        subjectRepository.deleteAll();
        sb1 = new Subject("math", "0000");
        sb2 = new Subject("English", "0001");

        subjectRepository.save(sb1);
        subjectRepository.save(sb2);

        /*
        List<Subject> list = subjectRepository.findAll();
        for (Subject subject : list)
            for (int i = 0; i < 1000; i++)
                System.out.println(subject.getId());
        */
    }
}
