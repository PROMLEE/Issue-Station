package com.issuestation.Controller;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Dto.ResponseDto;
import com.issuestation.Dto.UserDto.Token.TokenResponseDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.Project;
import com.issuestation.Entity.enums.Status;
import com.issuestation.Repository.IssueRepository;
import com.issuestation.Repository.ProjectRepository;
import com.issuestation.Security.TokenProvider;
import com.issuestation.Service.IssueService.IssueCreateService;
import com.issuestation.apiPayload.ApiResponse;
import com.issuestation.apiPayload.exception.handler.TempHandler;
import com.issuestation.converter.IssueCreateConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IssueControllerTest {

    @MockBean
    private TokenProvider tokenProvider; // TokenProvider 모의 객체 추가


    @DisplayName("이슈 생성 성공")
    @Test
    void issueCreateSuccess() throws Exception {
        // given
        long projectId = 1L;
        String jwtToken = "Bearer " + tokenProvider.createJwt(1L); // 유효한 JWT 토큰 생성
        System.out.println(jwtToken);

    }

    @Test
    void testJoin() {
    }

    @Test
    void searchIssues() {
    }

    @Test
    void info() {
    }

    @Test
    void changeState() {
    }

    @Test
    void createComment() {
    }

    @Test
    void assignAssigneeToIssue() {
    }

    @Test
    void assignReporterToIssue() {
    }

    @Test
    void getCommentsByIssueId() {
    }
}