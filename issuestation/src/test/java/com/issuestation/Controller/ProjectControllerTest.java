package com.issuestation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Entity.Project;
import com.issuestation.Entity.Team;
import com.issuestation.Entity.User;
import com.issuestation.Entity.enums.Role;
import com.issuestation.Security.TokenProvider;
import com.issuestation.Service.ProjectService.*;
import com.issuestation.apiPayload.ApiResponse;
import com.issuestation.apiPayload.code.status.ErrorStatus;
import com.issuestation.apiPayload.exception.handler.TempHandler;
import com.issuestation.converter.ProjectConverter;
import com.issuestation.converter.TeamConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectCreateService projectCreateService;
    @Mock
    private TeamJoinService teamJoinService;
    @Mock
    private ProjectSearchService projectSearchService;
    @Mock
    private ProjectInfoService projectInfoService;
    @Mock
    private MyProjectService myProjectService;
    @Mock
    private TeamMemberService teamMemberService;
    @Mock
    private MyRoleService myRoleService;

    @Mock
    private TokenProvider tokenProvider;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @DisplayName("내 프로젝트 목록 조회 성공")
    @Test
    void getUserProjectsSuccess() throws Exception {
        // given
        // 사용자의 프로젝트 목록이 없는 경우를 가정하여 빈 목록 생성
        List<ProjectResponseDto.MyProjectResponseDto> projects = Collections.emptyList();

        // 사용자의 프로젝트 목록을 반환하도록 설정
        when(myProjectService.getProjectsByUserToken(anyString())).thenReturn(projects);

        // when & then
        // 사용자의 프로젝트 목록 조회 요청을 보내고, 응답이 정상적으로 이루어졌는지 확인
        mockMvc.perform(get("/project/my")
                        .header("Authorization", "Bearer validToken"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(projects)));
    }

    @DisplayName("프로젝트 목록 조회 성공")
    @Test
    void getTeamsByProjectSuccess() throws Exception {
        // given
        // 팀 멤버 DTO 리스트 생성
        List<ProjectResponseDto.TeamMemberDTO> teamMemberDTOs = Collections.emptyList();
        // ResponseEntity로 감싼 응답 생성
        ResponseEntity<List<ProjectResponseDto.TeamMemberDTO>> responseEntity = ResponseEntity.ok(teamMemberDTOs);

        // 프로젝트 ID를 기준으로 팀 멤버 조회 서비스가 응답을 반환하도록 설정
        when(teamMemberService.getTeamsByProjectId(anyLong())).thenReturn(responseEntity);

        // when & then
        // 특정 프로젝트의 팀 멤버 조회 요청을 보내고, 응답이 정상적으로 이루어졌는지 확인
        mockMvc.perform(get("/project/team/member/1"))
                .andExpect(status().isOk()) // HTTP 상태코드가 200인지 확인
                .andExpect(content().string(objectMapper.writeValueAsString(teamMemberDTOs))); // 응답 본문이 예상한 값과 일치하는지 확인
    }
    @DisplayName("프로젝트 검색 성공")
    @Test
    void searchProjectsSuccess() throws Exception {
        // given
        // 테스트용 프로젝트 목록 생성
        List<Project> projects = Arrays.asList(
                Project.builder().id(1L).name("Project 1").description("Description 1").isPrivate(true).build(),
                Project.builder().id(2L).name("Project 2").description("Description 2").isPrivate(false).build()
        );

        // 프로젝트 검색 서비스의 searchAllProjects 메서드가 테스트용 프로젝트 목록을 반환하도록 설정
        given(projectSearchService.searchAllProjects()).willReturn(projects);

        // when & then
        // 예상되는 JSON 형식의 결과와 실제 요청으로 얻은 JSON 결과를 비교
        String expectedJson = objectMapper.writeValueAsString(projects);
        String actualJson = mockMvc.perform(get("/project/search"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // JSON 결과가 예상한 것과 동일한지 확인
        JSONAssert.assertEquals(expectedJson, actualJson, false);
    }

    @DisplayName("프로젝트 정보 조회 성공")
    @Test
    void getProjectInfoSuccess() throws Exception {
        // given
        // 특정 프로젝트 정보 생성
        Project project = new Project();
        // 프로젝트 정보를 감싼 API 응답 생성
        ApiResponse<Project> response = new ApiResponse<>(true, "200", "Project retrieved successfully", project);

        // 프로젝트 정보 조회 서비스가 특정 프로젝트 정보를 반환하도록 설정
        when(projectInfoService.projectInfo(anyLong())).thenReturn(project);

        // when & then
        // 특정 프로젝트 정보를 조회하는 요청을 보내고, 응답이 정상적으로 이루어졌는지 확인
        mockMvc.perform(get("/project/info/1"))
                .andExpect(status().isOk()) // HTTP 상태코드가 200인지 확인
                .andExpect(content().string(objectMapper.writeValueAsString(response))); // 응답 본문이 예상한 값과 일치하는지 확인
    }


}
