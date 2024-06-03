package com.issuestation.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Dto.ResponseDto;
import com.issuestation.Dto.UserDto.Token.TokenResponseDto;
import com.issuestation.Entity.*;
import com.issuestation.Entity.enums.CommentTag;
import com.issuestation.Entity.enums.Priority;
import com.issuestation.Entity.enums.Status;
import com.issuestation.Repository.ProjectRepository;
import com.issuestation.Security.TokenProvider;
import com.issuestation.Service.AuthService;
import com.issuestation.Service.IssueService.*;
import com.issuestation.converter.IssueCreateConverter;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueCreateService issueCreateService;

    @MockBean
    private IssueDeleteService issueDeleteService;

    @MockBean
    private IssueSearchService issueSearchService;

    @MockBean
    private IssueInfoServiceImpl issueInfoService;

    @MockBean
    private IssueStateService issueStateService;

    @MockBean
    private CommentCreateService commentCreateService;

    @MockBean
    private SetAssigneeService setAssigneeService;

    @MockBean
    private CommentListService commentListService;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("이슈 생성 성공")
    void issueCreateSuccess() throws Exception {
        // 준비
        long projectId = 1L;
        long issueId = 1L;
        IssueRequestDto.JoinIssueCreateRequestDto requestDto = new IssueRequestDto.JoinIssueCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "name", "test");
        ReflectionTestUtils.setField(requestDto, "description", "test");
        ReflectionTestUtils.setField(requestDto,"priority",Priority.MAJOR);
        Issue issue = new Issue();
        ReflectionTestUtils.setField(issue, "id", issueId); // Issue의 id 필드에 접근하여 값을 설정
        String jwtToken = "Bearer test.jwt.token";

        when(tokenProvider.validateJwt(anyString())).thenReturn("1"); // 토큰 검증 모의 처리
        when(issueCreateService.joinIssue(any(IssueRequestDto.JoinIssueCreateRequestDto.class), anyLong())).thenReturn(issue); // 이슈 생성 서비스 호출 결과 모의 처리

        // 실행 & 검증
        mockMvc.perform(post("/issue/create/{id}", projectId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(issueId));
    }
    @Test
    @DisplayName("이슈 삭제 성공")
    void issueDeleteSuccess() throws Exception {
        long issueId = 1L;
        String jwtToken = "Bearer test.jwt.token";

        when(tokenProvider.validateJwt(anyString())).thenReturn("1"); // 토큰 검증 모의 처리

        mockMvc.perform(delete("/issue/delete/{id}", issueId)
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(issueId));

        verify(issueDeleteService).deleteIssue(issueId);
    }

    @Test
    @DisplayName("이슈 목록 조회 성공 - 이름과 상태로 검색")
    void searchIssuesByNameAndStatusSuccess() throws Exception {
        long projectId = 1L;
        String name = "이슈";
        String status = "OPEN"; // 예시 상태 값
        String jwtToken = "Bearer test.jwt.token";

        List<IssueResponseDto.JoinIssueSearchResponseDto> responseDtoList = List.of(
                IssueResponseDto.JoinIssueSearchResponseDto.builder()
                        .id(1L)
                        .name("이슈1")
                        .description("이슈 설명1")
                        .status(Status.NEW) // 예시 상태 값
                        .projectId(projectId)
                        .priority(Priority.BLOCKER)
                        .modDate("2023-06-01")
                        .build()
        );

        when(tokenProvider.validateJwt(anyString())).thenReturn("1"); // 토큰 검증 모의 처리
        when(issueSearchService.searchIssuesByProjectIdNameAndStatus(projectId, name, status)).thenReturn(responseDtoList);

        mockMvc.perform(get("/issue/search/{id}", projectId)
                        .header("Authorization", jwtToken)
                        .param("name", name)
                        .param("status", status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("이슈1"))
                .andExpect(jsonPath("$[0].status").value("NEW"));
    }

    @Test
    @DisplayName("이슈 목록 조회 성공 - 프로젝트 ID로만 검색")
    void searchIssuesByProjectIdSuccess() throws Exception {
        long projectId = 1L;
        String jwtToken = "Bearer test.jwt.token";

        List<IssueResponseDto.JoinIssueSearchResponseDto> responseDtoList = List.of(
                IssueResponseDto.JoinIssueSearchResponseDto.builder()
                        .id(1L)
                        .name("이슈1")
                        .description("이슈 설명1")
                        .status(Status.NEW) // 예시 상태 값
                        .priority(Priority.CRITICAL)
                        .projectId(projectId)
                        .modDate("2023-06-01")
                        .build()
        );

        when(tokenProvider.validateJwt(anyString())).thenReturn("1"); // 토큰 검증 모의 처리
        when(issueSearchService.searchIssuesByProjectId(projectId)).thenReturn(responseDtoList);

        mockMvc.perform(get("/issue/search/{id}", projectId)
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("이슈1"))
                .andExpect(jsonPath("$[0].status").value("NEW"));
    }

    @Test
    @DisplayName("이슈 정보 조회 성공")
    void issueInfoSuccess() throws Exception {
        long issueId = 1L;
        String jwtToken = "Bearer test.jwt.token";

        IssueResponseDto.JoinIssueInfoResponseDto responseDto = IssueResponseDto.JoinIssueInfoResponseDto.builder()
                .id(issueId)
                .name("테스트 이슈")
                .description("테스트 설명")
                .status(Status.NEW)
                .priority(Priority.TRIVIAL)
                .projectId(1L)
                .initDate("2023-06-01")
                .modDate("2023-06-01")
                .assignee("테스트 담당자")
                .reporter("테스트 보고자")
                .build();

        when(tokenProvider.validateJwt(anyString())).thenReturn("1"); // 토큰 검증 모의 처리
        when(issueInfoService.getIssueDetails(issueId)).thenReturn(responseDto); // 이슈 정보 서비스 호출 결과 모의 처리

        // 실행 & 검증
        mockMvc.perform(get("/issue/info/{id}", issueId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(issueId))
                .andExpect(jsonPath("$.result.name").value("테스트 이슈"))
                .andExpect(jsonPath("$.result.description").value("테스트 설명"))
                .andExpect(jsonPath("$.result.status").value("NEW"))
                .andExpect(jsonPath("$.result.priority").value("TRIVIAL"))
                .andExpect(jsonPath("$.result.projectId").value(1L))
                .andExpect(jsonPath("$.result.initDate").value("2023-06-01"))
                .andExpect(jsonPath("$.result.modDate").value("2023-06-01"))
                .andExpect(jsonPath("$.result.assignee").value("테스트 담당자"))
                .andExpect(jsonPath("$.result.reporter").value("테스트 보고자"));

    }

    @Test
    @DisplayName("이슈 상태 변경 성공")
    void changeIssueStateSuccess() throws Exception {
        // 준비
        long issueId = 1L; // 테스트 대상 이슈 ID
        Status newStatus = Status.FIXED; // 변경할 새 상태
        IssueRequestDto.JoinIssueStateRequestDto requestDto = new IssueRequestDto.JoinIssueStateRequestDto();
        ReflectionTestUtils.setField(requestDto, "status", newStatus); // 변경할 상태로 수정, 즉, 변경할 상태를 전달받음

        //서비스에서 상태를 return하는게 없기 때문에, 상태 변경 서비스가 동작하는지만 확인해야함.

        String jwtToken = "Bearer test.jwt.token";

        when(tokenProvider.validateJwt(anyString())).thenReturn("1"); // 토큰 검증 모의 처리

        // 실행 & 검증
        mockMvc.perform(post("/issue/state/{id}", issueId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // 이슈 상태 변경 서비스가 올바르게 호출되었는지 확인
        verify(issueStateService).changeIssueState(any(IssueRequestDto.JoinIssueStateRequestDto.class), eq(issueId));
    }


    @Test
    @DisplayName("코멘트 생성 성공")
    void createCommentSuccess() throws Exception {
        // 준비
        long issueId = 1L;
        long commentId = 1L;
        String jwtToken = "Bearer test.jwt.token";

        IssueRequestDto.JoinCommentCreateRequestDto requestDto = new IssueRequestDto.JoinCommentCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "comment", "This is a test comment");
        ReflectionTestUtils.setField(requestDto, "tag", CommentTag.ISSUE_CREATED);

        Comment comment = new Comment();
        ReflectionTestUtils.setField(comment, "id", commentId);

        when(tokenProvider.validateJwt(anyString())).thenReturn("1"); // 토큰 검증 모의 처리
        when(commentCreateService.createComment(any(IssueRequestDto.JoinCommentCreateRequestDto.class), eq(issueId), anyString())).thenReturn(comment); // 코멘트 생성 서비스 호출 결과 모의 처리

        // 실행 & 검증
        mockMvc.perform(post("/issue/comment/create/{id}", issueId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(commentId));
    }

    @Test
    @DisplayName("담당자 할당 성공")
    void assignAssigneeSuccess() throws Exception {
        // 준비
        long issueId = 1L; // 테스트에 사용할 이슈 ID
        String nickname = "testUser"; // 테스트에 사용할 담당자 닉네임
        IssueRequestDto.JoinAssigneeRequestDto requestDto = new IssueRequestDto.JoinAssigneeRequestDto();
        ReflectionTestUtils.setField(requestDto, "nickname", nickname); // Request DTO에 닉네임 설정

        Assignee assignee = new Assignee(); // Assignee 엔티티 모의 객체
        User user = new User();
        ReflectionTestUtils.setField(user, "nickname", nickname); // User에 닉네임 설정
        ReflectionTestUtils.setField(assignee, "user", user); // Assignee에 User 설정

        when(setAssigneeService.assignAssignee(eq(issueId), any(IssueRequestDto.JoinAssigneeRequestDto.class))).thenReturn(assignee); // 담당자 할당 서비스 호출 결과 모의 처리

        // 실행 & 검증
        mockMvc.perform(post("/issue/assignee/{id}", issueId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.nickname").value(nickname)); // 응답에서 닉네임 검증
    }

    @Test
    @DisplayName("리포터 할당 성공")
    void assignReporterToIssueSuccess() throws Exception {
        // 준비
        long issueId = 1L; // 테스트를 위한 이슈 ID
        String nickname = "testReporter"; // 할당할 리포터의 닉네임

        IssueRequestDto.JoinAssigneeRequestDto requestDto = new IssueRequestDto.JoinAssigneeRequestDto();
        ReflectionTestUtils.setField(requestDto, "nickname", nickname); // 리포터의 닉네임 설정

        Reporter reporter = new Reporter(); // Reporter 엔티티 생성 및 설정
        // Reporter 엔티티에 필요한 필드 설정 생략
        User user = new User(); // User 객체 생성 및 설정
        ReflectionTestUtils.setField(user, "nickname", nickname); // User에 닉네임 설정
        ReflectionTestUtils.setField(reporter, "user", user); // Reporter에 User 설정

        when(setAssigneeService.assignReporter(eq(issueId), any(IssueRequestDto.JoinAssigneeRequestDto.class)))
                .thenReturn(reporter); // assignReporter 호출 결과 모의 처리

        String jwtToken = "Bearer test.jwt.token"; // 테스트용 JWT 토큰

        // 실행 & 검증
        mockMvc.perform(post("/issue/reporter/{id}", issueId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.nickname").value(nickname));
    }

    @Test
    @DisplayName("특정 이슈의 댓글 목록 조회 성공")
    void getCommentsByIssueIdSuccess() throws Exception {
        long issueId = 1L;
        List<IssueResponseDto.GetCommentResponseDto> comments = List.of(
                IssueResponseDto.GetCommentResponseDto.builder()
                        .id(1L)
                        .comment("Test comment 1")
                        .tag(CommentTag.ISSUE_CREATED)
                        .nickname("user1")
                        .modDate("2023-06-01T10:00:00")
                        .build(),
                IssueResponseDto.GetCommentResponseDto.builder()
                        .id(2L)
                        .comment("Test comment 2")
                        .tag(CommentTag.SET_FIXED)
                        .nickname("user2")
                        .modDate("2023-06-01T11:00:00")
                        .build()
        );

        when(commentListService.getCommentsByIssueId(issueId)).thenReturn(comments);

        mockMvc.perform(get("/issue/comment/{id}", issueId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(comments.size()))
                .andExpect(jsonPath("$[0].id").value(comments.get(0).getId()))
                .andExpect(jsonPath("$[0].comment").value(comments.get(0).getComment()))
                .andExpect(jsonPath("$[0].tag").value(comments.get(0).getTag().toString()))
                .andExpect(jsonPath("$[0].nickname").value(comments.get(0).getNickname()))
                .andExpect(jsonPath("$[0].modDate").value(comments.get(0).getModDate()))
                .andExpect(jsonPath("$[1].id").value(comments.get(1).getId()))
                .andExpect(jsonPath("$[1].comment").value(comments.get(1).getComment()))
                .andExpect(jsonPath("$[1].tag").value(comments.get(1).getTag().toString()))
                .andExpect(jsonPath("$[1].nickname").value(comments.get(1).getNickname()))
                .andExpect(jsonPath("$[1].modDate").value(comments.get(1).getModDate()));
    }
}