package com.example.be_kwangwoon;

import com.example.be_kwangwoon.domain.bookmark.domain.Bookmark;
import com.example.be_kwangwoon.domain.bookmark.repository.BookmarkRepository;
import com.example.be_kwangwoon.domain.comment.domain.Comment;
import com.example.be_kwangwoon.domain.comment.domain.Used;
import com.example.be_kwangwoon.domain.comment.dto.AddCommentRequest;
import com.example.be_kwangwoon.domain.comment.dto.CommentResponse;
import com.example.be_kwangwoon.domain.comment.dto.UpdateCommentRequest;
import com.example.be_kwangwoon.domain.comment.repository.CommentRepository;
import com.example.be_kwangwoon.domain.department.domain.Department;
import com.example.be_kwangwoon.domain.department.repository.DepartmentRepository;
import com.example.be_kwangwoon.domain.post.domain.Post;
import com.example.be_kwangwoon.domain.post.domain.Status;
import com.example.be_kwangwoon.domain.post.domain.Type;
import com.example.be_kwangwoon.domain.post.dto.AddPostRequest;
import com.example.be_kwangwoon.domain.post.dto.UpdatePostRequest;
import com.example.be_kwangwoon.domain.post.repository.PostRepository;
import com.example.be_kwangwoon.domain.professor.domain.Professor;
import com.example.be_kwangwoon.domain.professor.repository.ProfessorRepository;
import com.example.be_kwangwoon.domain.semester.domain.Semester;
import com.example.be_kwangwoon.domain.semester.repository.SemesterRepository;
import com.example.be_kwangwoon.domain.subject.domain.Subject;
import com.example.be_kwangwoon.domain.subject.dto.FindSubjectBySemesterRequest;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.type.TypeReference;

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

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    SemesterRepository semesterRepository;

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
                .nickname("harry")
                .isCertification(Certification.Y)
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @BeforeEach
    public void addSubject() {
        createDefaultSubject();
    }

//    @DisplayName("addPost: 아티클 추가에 성공한다.")
//    @Test
//    public void addPost() throws Exception {
//
//        // given
//        final String url = "/posts";
//        final String title = "title";
//        final String content = "content";
//        final Status status = Status.ACTIVE;
//        final LocalDateTime deadline = LocalDateTime.MAX;
//        final int views = 0;
//        final String contact = "123";
//        final int people = 3;
//        final Long subjectId = (long) 1;
//
//        final AddPostRequest userRequest = new AddPostRequest(title, content, status, deadline, views, contact, people, subjectId);
//
//        final String requestBody = objectMapper.writeValueAsString(userRequest);
//
//        Principal principal = Mockito.mock(Principal.class);
//        Mockito.when(principal.getName()).thenReturn("username");
//
//        // when
//        ResultActions result = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .principal(principal)
//                .content(requestBody));
//
//        // then
//        result.andExpect(status().isCreated());
//
//        List<Post> articles = postRepository.findAll();
//
//        assertThat(articles.size()).isEqualTo(1);
//        assertThat(articles.get(0).getTitle()).isEqualTo(title);
//        assertThat(articles.get(0).getContent()).isEqualTo(content);
//    }

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
        //.andExpect(jsonPath("$[0].type").value(savedPost.getType()))
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
        //.andExpect(jsonPath("$.type").value(savedArticle.getType()))
    }

//    @DisplayName("updatePost: 아티클 수정에 성공한다.")
//    @Test
//    public void updatePost() throws Exception {
//        // given
//        final String url = "/posts/{id}";
//        Post savedArticle = createDefaultPost(LocalDateTime.MAX);
//
//        final String newTitle = "new title";
//        final String newContent = "new content";
//        final Status status = Status.INACTIVE;
//        final LocalDateTime deadline = LocalDateTime.MAX;
//        final String contact = "000";
//        final int people = 0;
//        final Long subjectId = (long) 6;
//
//
//        UpdatePostRequest request = new UpdatePostRequest(newTitle, newContent, status, deadline, contact, people, subjectId);
//
//        // when
//        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(request)));
//
//        // then
//        //result.andExpect(status().isOk());
//
//        Post article = postRepository.findById(savedArticle.getId()).get();
//
//        assertThat(article.getTitle()).isEqualTo(newTitle);
//        assertThat(article.getContent()).isEqualTo(newContent);
//        assertThat(article.getStatus()).isEqualTo(status);
//        //assertThat(article.getDeadline()).isEqualTo(deadline); db에서 저장되는 값과 java에서 출력하는 값이 다르게 나옴. 올바르게 진행됐어서 오류 표출
//        assertThat(article.getContact()).isEqualTo(contact);
//        assertThat(article.getPeople()).isEqualTo(people);
//    }


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

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .principal(principal));

        resultActions.andExpect(status().isOk());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(post.getContent()))
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].contact").value(post.getContact()));
        bookmarkRepository.deleteAll();
    }

    @DisplayName("addComment: 댓글을 다는데 성공")
    @Test
    public void addComment() throws Exception {
        final String url = "/posts/{postId}/comment";

        Post post = createDefaultPost(LocalDateTime.MIN);

        AddCommentRequest userRequest = new AddCommentRequest("content", (long) 0, Used.Y);

        String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        ResultActions resultActions = mockMvc.perform(post(url, post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        List<Comment> list = commentRepository.findAll();

        resultActions.andExpect(status().isCreated());
        assertThat(list.get(0).getContent()).isEqualTo("content");

        userRequest = new AddCommentRequest("content2", list.get(0).getId(), Used.Y);

        requestBody = objectMapper.writeValueAsString(userRequest);

        resultActions = mockMvc.perform(post(url, post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        list = commentRepository.findAll();

        resultActions.andExpect(status().isCreated());
        assertThat(list.get(0).getContent()).isEqualTo("content");
        assertThat(list.get(1).getContent()).isEqualTo("content2");

        commentRepository.deleteAll();
    }

    @DisplayName("updateComment: 댓글을 수정하는데 성공")
    @Test
    public void updateComment() throws Exception {
        final String url = "/posts/{postId}/{commentId}";

        Post post = createDefaultPost(LocalDateTime.MIN);
        Comment comment = createDefaultComment(null, post);

        final String content = "helloworld";
        final Used used = Used.N;

        UpdateCommentRequest request = new UpdateCommentRequest(content, used);

        ResultActions result = mockMvc.perform(put(url, post.getId(), comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        result.andExpect(status().isOk());

        List<Comment> list = commentRepository.findAll();
        assertThat(list.get(0).getContent()).isEqualTo("helloworld");
        assertThat(list.get(0).getUsed()).isEqualTo(Used.N);
        commentRepository.deleteAll();
    }

    @DisplayName("findAllComment: 댓글을 모두 가져오는데 성공")
    @Test
    public void findAllComment() throws Exception {
        final String url = "/posts/{postId}/comment";

        Post post = createDefaultPost(LocalDateTime.MIN);

        AddCommentRequest userRequest = new AddCommentRequest("content", (long) 0, Used.Y);

        String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        ResultActions resultActions = mockMvc.perform(post(url, post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));
        // 부모 댓글 추가

        List<Comment> list = commentRepository.findAll();

        resultActions.andExpect(status().isCreated());
        assertThat(list.get(0).getContent()).isEqualTo("content");
        // 부모 댓글 올바르게 추가 되었는지 확인

        userRequest = new AddCommentRequest("content2", list.get(0).getId(), Used.Y);

        requestBody = objectMapper.writeValueAsString(userRequest);

        resultActions = mockMvc.perform(post(url, post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));
        // 답글 추가

        resultActions.andExpect(status().isCreated());

        resultActions = mockMvc.perform(get(url, post.getId())
                .accept(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("content"))
                .andExpect(jsonPath("$[0].username").value("harry"))
                .andExpect(jsonPath("$[1].content").value("content2"))
                .andExpect(jsonPath("$[1].username").value("harry"));
        //        .andExpect(jsonPath("$[1].createdtime").value(LocalDateTime.MIN))
        // 부모 댓글과 답글 올바르게 추가되었는지 확인

        /*
        MvcResult mvcResult = resultActions.andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<CommentResponse> comments = objectMapper.readValue(jsonResponse, new TypeReference<List<CommentResponse>>() {
        });
        // resultActions으로 반환된 값을 List<CommentResponse> 형태로 변환

        Comment comment = commentRepository.findById(comments.get(0).getId()).orElseThrow(() -> new IllegalArgumentException("not found : " + comments.get(0).getId()));
        assertThat(comment.getContent()).isEqualTo("content");
        // 부모 댓글의 id가 올바르게 반환되었느지 확인

        CommentResponse firstComment = null;
        if (!comments.isEmpty()) {
            firstComment = comments.get(0);
        }
        List<Long> childs = firstComment.getChildsIds();
        comment = commentRepository.findById(childs.get(0)).orElseThrow(() -> new IllegalArgumentException("not found : " + childs.get(0)));
        assertThat(comment.getContent()).isEqualTo("content2");*/
        // 부모 댓글의 답글 목록이 올바르게 반환되었느지 확인
        commentRepository.deleteAll();
    }

    @DisplayName("deleteComment: 댓글 삭제에 성공했다")
    @Test
    public void deleteComment() throws Exception {
        final String url = "/posts/{postId}/{commentId}";

        Post post = createDefaultPost(LocalDateTime.MIN);
        Comment comment = createDefaultComment(null, post);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        List<Comment> comments = commentRepository.findAll();
        assertThat(comments.get(0).getContent()).isEqualTo(comment.getContent());

        ResultActions resultActions = mockMvc.perform(delete(url, post.getId(), comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal));

        comments = commentRepository.findAll();

        assertThat(comments).isEmpty();
    }

    /*
    @DisplayName("findAllSubject/department/semester: subject/department/semester 목록을 불러오는데 성공")
    @Test
    public void findAllSubject_Department_Semester() throws Exception {
        String url = "/subject";

        subjectRepository.deleteAll();

        Department department = departmentRepository.save(new Department("software"));
        Professor professor = professorRepository.save(new Professor("harry", department));
        Semester semester = semesterRepository.save(new Semester("2023", "2"));

        sb1 = new Subject("math", "0000", department, semester, professor);
        sb2 = new Subject("English", "0001", department, semester, professor);

        subjectRepository.save(sb1);
        subjectRepository.save(sb2);

        ResultActions resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(sb1.getName()))
                .andExpect(jsonPath("$[0].code").value(sb1.getCode()))
                .andExpect(jsonPath("$[0].departments_name").value(department.getName()))
                .andExpect(jsonPath("$[0].professors_name").value(professor.getName()))
                .andExpect(jsonPath("$[0].semesters_years").value(semester.getYears()))
                .andExpect(jsonPath("$[0].semesters_semester").value(semester.getSemester()))
                .andExpect(jsonPath("$[1].name").value(sb2.getName()))
                .andExpect(jsonPath("$[1].code").value(sb2.getCode()))
                .andExpect(jsonPath("$[1].departments_name").value(department.getName()))
                .andExpect(jsonPath("$[1].professors_name").value(professor.getName()))
                .andExpect(jsonPath("$[1].semesters_years").value(semester.getYears()))
                .andExpect(jsonPath("$[1].semesters_semester").value(semester.getSemester()));

        url = "/department";

        resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].id").value(department.getId()))
                .andExpect(jsonPath("$[1].name").value(department.getName()));

        url = "/semester";

        resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].years").value(semester.getYears()))
                .andExpect(jsonPath("$[1].semester").value(semester.getSemester()));

        url = "/subjectbysemester";

        FindSubjectBySemesterRequest findSubjectBySemesterRequest = new FindSubjectBySemesterRequest(semester.getYears(), semester.getSemester());

        String requestBody = objectMapper.writeValueAsString(findSubjectBySemesterRequest);

        resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(sb1.getName()))
                .andExpect(jsonPath("$[0].code").value(sb1.getCode()))
                .andExpect(jsonPath("$[0].departments_name").value(department.getName()))
                .andExpect(jsonPath("$[0].professors_name").value(professor.getName()))
                .andExpect(jsonPath("$[0].semesters_years").value(semester.getYears()))
                .andExpect(jsonPath("$[0].semesters_semester").value(semester.getSemester()))
                .andExpect(jsonPath("$[1].name").value(sb2.getName()))
                .andExpect(jsonPath("$[1].code").value(sb2.getCode()))
                .andExpect(jsonPath("$[1].departments_name").value(department.getName()))
                .andExpect(jsonPath("$[1].professors_name").value(professor.getName()))
                .andExpect(jsonPath("$[1].semesters_years").value(semester.getYears()))
                .andExpect(jsonPath("$[1].semesters_semester").value(semester.getSemester()));
    }
     */


    @DisplayName("findPostBySemester: =semester로 Post 목록을 불러오는데 성공")
    @Test
    public void findPostBySemester() throws Exception {
        final String url = "/posts/filter/{semesterId}";

        subjectRepository.deleteAll();

        Department department = departmentRepository.save(new Department("software"));
        Professor professor = professorRepository.save(new Professor("harry", department));
        Semester semester = semesterRepository.save(new Semester("2023", "2"));

        sb1 = new Subject("math", "0000", department, semester, professor);
        sb2 = new Subject("English", "0001", department, semester, professor);

        subjectRepository.save(sb1);
        subjectRepository.save(sb2);

        Post post = createDefaultPost(LocalDateTime.MIN);

        ResultActions resultActions = mockMvc.perform(get(url, semester.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post.getContent()));
    }


    @DisplayName("findPostByDepartment: department로 post 목록을 불러오는데 성공")
    @Test
    public void findPostByDepartment() throws Exception {
        final String url = "/posts/filter2/{departmentId}";

        subjectRepository.deleteAll();

        Department department = departmentRepository.save(new Department("software"));
        Professor professor = professorRepository.save(new Professor("harry", department));
        Semester semester = semesterRepository.save(new Semester("2023", "2"));

        sb1 = new Subject("math", "0000", department, semester, professor);
        sb2 = new Subject("English", "0001", department, semester, professor);

        subjectRepository.save(sb1);
        subjectRepository.save(sb2);

        Post post = createDefaultPost(LocalDateTime.MIN);

        ResultActions resultActions = mockMvc.perform(get(url, department.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post.getContent()));
    }

    /*
    @DisplayName("findPostBySubjectName: SubjectName로 post 목록을 불러오는데 성공")
    @Test
    public void findPostBySubjectName() throws Exception {
        final String url = "/posts/filter3/{subjectmentName}";

        subjectRepository.deleteAll();

        Department department = departmentRepository.save(new Department("software"));
        Professor professor = professorRepository.save(new Professor("harry", department));
        Semester semester = semesterRepository.save(new Semester("2023", "2"));

        sb1 = new Subject("오픈소스소프트웨어실습", "0000", department, semester, professor);
        sb2 = new Subject("English", "0001", department, semester, professor);

        subjectRepository.save(sb1);
        subjectRepository.save(sb2);

        Post post = createDefaultPost(LocalDateTime.MIN);

        ResultActions resultActions = mockMvc.perform(get(url, sb1.getName())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post.getContent()));
    }
     */

    @DisplayName("add/deletebookmarkbypostId: bookmark를  불러오고 삭제하는데 성공")
    @Test
    public void add_deleteBookmark() throws Exception {
        final String url = "/posts/{postId}/interest";
        System.out.println(1);
        Post post = createDefaultPost(LocalDateTime.MIN);
        Bookmark bookmark = bookmarkRepository.save(new Bookmark(user, post));

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        ResultActions resultActions = mockMvc.perform(post(url, post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal));

        resultActions
                .andExpect(status().isOk());

        List<Bookmark> list = bookmarkRepository.findAll();
        assertThat(list.get(0).getId()).isEqualTo(bookmark.getId());

        resultActions = mockMvc.perform(delete(url, post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal));

        resultActions
                .andExpect(status().isOk());

        list = bookmarkRepository.findAll();
        assertThat(list.size()).isEqualTo(0);
    }


    Comment createDefaultComment(Comment comment, Post post) {
        return commentRepository.save(Comment.builder()
                .content("content")
                .user(user)
                .post(post)
                .parentComment(comment)
                .used(Used.Y)
                .build());
    }

    Comment createDefaultComment(String content, Comment comment, Post post) {
        return commentRepository.save(Comment.builder()
                .content(content)
                .user(user)
                .post(post)
                .parentComment(comment)
                .used(Used.Y)
                .build());
    }

    Post createDefaultPost(LocalDateTime localDateTime) {
        return postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .status(Status.ACTIVE)
                .type(Type.PROJECT)
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
                .type(Type.STUDY)
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
