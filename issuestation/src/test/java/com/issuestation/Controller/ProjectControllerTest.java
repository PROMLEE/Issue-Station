package com.issuestation.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Entity.Project;
import com.issuestation.Entity.Team;
import com.issuestation.Entity.enums.Priority;
import com.issuestation.Entity.enums.Role;
import com.issuestation.Security.TokenProvider;
import com.issuestation.Service.ProjectService.*;
import com.issuestation.apiPayload.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectCreateService projectCreateService;

    @MockBean
    private TeamJoinService teamJoinService;

    @MockBean
    private TeamMemberService teamMemberService;

    @MockBean
    private ProjectSearchService projectSearchService;

    @MockBean
    private ProjectInfoService projectInfoService;

    @MockBean
    private MyProjectService myProjectService;

    @MockBean
    private MyRoleService myRoleService;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("프로젝트 생성 성공")
    void projectCreateSuccess() throws Exception {
        long projectId = 1L;
        ProjectRequestDto.JoinProjectCreateRequestDto requestDto = new ProjectRequestDto.JoinProjectCreateRequestDto();
        ReflectionTestUtils.setField(requestDto, "name", "test");
        ReflectionTestUtils.setField(requestDto, "description", "test");
        ReflectionTestUtils.setField(requestDto, "isPrivate", true);

        Project project = new Project();

        ReflectionTestUtils.setField(project, "id", projectId); // project id 필드에 접근하여 값을 설정

        String jwtToken = "Bearer test.jwt.token";

        when(tokenProvider.validateJwt(anyString())).thenReturn("1"); // 토큰 검증 모의 처리
        when(projectCreateService.joinProject(any(ProjectRequestDto.JoinProjectCreateRequestDto.class))).thenReturn(project); // 프로젝트 생성 서비스 호출 결과 모의 처리

        // 실행 & 검증
        mockMvc.perform(post("/project/create")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(projectId));
    }

    @Test
    @DisplayName("프로젝트 팀 가입 성공")
    void joinTeamSuccess() throws Exception {
        long projectId = 1L;
        ProjectRequestDto.JoinTeamRequestDto requestDto = new ProjectRequestDto.JoinTeamRequestDto();
        ReflectionTestUtils.setField(requestDto, "nickname", "userNickname");
        ReflectionTestUtils.setField(requestDto, "isAdmin", false);
        ReflectionTestUtils.setField(requestDto, "role", Role.DEVELOPER);

        Team team = new Team();
        ReflectionTestUtils.setField(team, "id", projectId); // team id 필드에 접근하여 값을 설정

        String jwtToken = "Bearer test.jwt.token";

        when(tokenProvider.validateJwt(anyString())).thenReturn("1"); // 토큰 검증 모의 처리
        when(teamJoinService.joinTeam(any(ProjectRequestDto.JoinTeamRequestDto.class), eq(projectId))).thenReturn(team); // 팀 가입 서비스 호출 결과 모의 처리

        // 실행 & 검증
        mockMvc.perform(post("/project/team/{id}", projectId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(projectId));
    }


    @Test
    @DisplayName("프로젝트 팀 멤버 조회 성공")
    void getTeamsByProject() throws Exception {
        long projectId = 1L;

        List<ProjectResponseDto.TeamMemberDTO> teamMembers = Arrays.asList(
                new ProjectResponseDto.TeamMemberDTO(true, Role.PL, "plNickname"),
                new ProjectResponseDto.TeamMemberDTO(false, Role.DEVELOPER, "developerNickname")
        );

        when(teamMemberService.getTeamsByProjectId(projectId)).thenReturn(ResponseEntity.ok(teamMembers));

        String jwtToken = "Bearer test.jwt.token";

        // 실행 & 검증
        mockMvc.perform(get("/project/team/member/{id}", projectId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isAdmin").value(true))
                .andExpect(jsonPath("$[0].role").value("PL"))
                .andExpect(jsonPath("$[0].nickname").value("plNickname"))
                .andExpect(jsonPath("$[1].isAdmin").value(false))
                .andExpect(jsonPath("$[1].role").value("DEVELOPER"))
                .andExpect(jsonPath("$[1].nickname").value("developerNickname"));
    }

    @Test
    @DisplayName("프로젝트 이름으로 검색 성공")
    void searchProjectsByNameSuccess() throws Exception {
        List<Project> projects = Arrays.asList(new Project(), new Project()); // 가정된 프로젝트 목록
        String searchName = "testProject";

        when(projectSearchService.searchProjectsByName(searchName)).thenReturn(projects); // 이름으로 프로젝트 검색 시 모의 처리

        mockMvc.perform(get("/project/search")
                        .param("name", searchName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(projects.size())); // 반환된 프로젝트 목록의 크기 검증
    }

    @Test
    @DisplayName("모든 프로젝트 검색 성공")
    void searchAllProjectsSuccess() throws Exception {
        List<Project> projects = Arrays.asList(new Project(), new Project(), new Project()); // 가정된 프로젝트 목록

        when(projectSearchService.searchAllProjects()).thenReturn(projects); // 모든 프로젝트 검색 시 모의 처리

        mockMvc.perform(get("/project/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(projects.size())); // 반환된 프로젝트 목록의 크기 검증
    }
    @Test
    @DisplayName("프로젝트 정보 조회 성공")
    void getProjectInfo() throws Exception {
        long projectId = 1L;

        Project project = new Project();
        ReflectionTestUtils.setField(project, "id", projectId);
        ReflectionTestUtils.setField(project, "name", "testProject");
        ReflectionTestUtils.setField(project, "description", "testDescription");

        ApiResponse<Project> response = new ApiResponse<>(true, "200", "Project retrieved successfully", project);

        when(projectInfoService.projectInfo(projectId)).thenReturn(project);

        // 실행 & 검증
        mockMvc.perform(get("/project/info/{id}", projectId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Project retrieved successfully"))
                .andExpect(jsonPath("$.result.id").value(projectId))
                .andExpect(jsonPath("$.result.name").value("testProject"))
                .andExpect(jsonPath("$.result.description").value("testDescription"));
    }
    @Test
    @DisplayName("사용자 프로젝트 조회 성공")
    void getUserProjectsSuccess() throws Exception {
        String jwtToken = "Bearer test.jwt.token";
        String token = "test.jwt.token";
        Long userId = 1L;
        List<ProjectResponseDto.MyProjectResponseDto> projects = Arrays.asList(
                ProjectResponseDto.MyProjectResponseDto.builder()
                        .id(1L).name("Project 1")
                        .isPrivate(false).description("Description 1")
                        .thumbnaillink("http://example.com/image1.png")
                        .initdate(LocalDateTime.now().minusDays(5))
                        .moddate(LocalDateTime.now().minusDays(3))
                        .build(),
                ProjectResponseDto.MyProjectResponseDto.builder()
                        .id(2L)
                        .name("Project 2")
                        .isPrivate(true)
                        .description("Description 2")
                        .thumbnaillink("http://example.com/image2.png")
                        .initdate(LocalDateTime.now().minusDays(10))
                        .moddate(LocalDateTime.now().minusDays(1))
                        .build()
        );
        when(tokenProvider.validateJwt(anyString())).thenReturn(userId.toString());
        when(myProjectService.getProjectsByUserToken(token)).thenReturn(projects);
        mockMvc.perform(get("/project/my")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Project 1"))
                .andExpect(jsonPath("$[0].isPrivate").value(false))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].thumbnaillink").value("http://example.com/image1.png"))
                .andExpect(jsonPath("$[0].initdate").exists()).andExpect(jsonPath("$[0].moddate").exists())
                .andExpect(jsonPath("$[1].id").value(2L)).andExpect(jsonPath("$[1].name").value("Project 2"))
                .andExpect(jsonPath("$[1].isPrivate").value(true))
                .andExpect(jsonPath("$[1].description").value("Description 2"))
                .andExpect(jsonPath("$[1].thumbnaillink").value("http://example.com/image2.png"))
                .andExpect(jsonPath("$[1].initdate").exists()).andExpect(jsonPath("$[1].moddate").exists());
    }

    @Test
    @DisplayName("프로젝트에서의 역할 조회 성공")
    void getMyRoleInProjectSuccess() throws Exception {
        long projectId = 1L;
        long userId = 1L;
        Role role = Role.DEVELOPER; // 직접 enum 값을 사용

        String jwtToken = "Bearer test.jwt.token";

        when(tokenProvider.validateJwt(anyString())).thenReturn(String.valueOf(userId)); // 토큰 검증 모의 처리
        when(myRoleService.findUserRoleInProject(projectId, userId)).thenReturn(role); // 역할 조회 서비스 호출 결과 모의 처리

        // 실행 & 검증
        mockMvc.perform(get("/project/role/{id}", projectId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Role retrieved successfully"))
                .andExpect(jsonPath("$.result").value(role.name())); // enum의 이름을 문자열로 비교
    }


}