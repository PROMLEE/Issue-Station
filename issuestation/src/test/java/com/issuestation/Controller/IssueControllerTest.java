package com.issuestation.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Dto.ResponseDto;
import com.issuestation.Dto.UserDto.Token.TokenResponseDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.Project;
import com.issuestation.Entity.enums.Status;
import com.issuestation.Repository.ProjectRepository;
import com.issuestation.Security.TokenProvider;
import com.issuestation.Service.AuthService;
import com.issuestation.Service.IssueService.IssueCreateService;
import com.issuestation.converter.IssueCreateConverter;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.mockStatic;

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

@SpringBootTest
@AutoConfigureMockMvc
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueCreateService issueCreateService;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("이슈 생성 성공")
    void issueCreateSuccess() throws Exception {
        // 준비
        long projectId = 1L;
        long issueId = 1L;
        IssueRequestDto.JoinIssueCreateRequestDto requestDto = new IssueRequestDto.JoinIssueCreateRequestDto("새 이슈", "이슈 설명");
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
}