package com.issuestation.Controller;

import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Entity.Project;
import com.issuestation.Security.TokenProvider;
import com.issuestation.Service.ProjectService.*;
import com.issuestation.apiPayload.ApiResponse;
import com.issuestation.apiPayload.code.status.ErrorStatus;
import com.issuestation.apiPayload.exception.handler.TempHandler;
import com.issuestation.converter.ProjectConverter;
import com.issuestation.converter.TeamConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectCreateService projectCreateService;

    private final TeamJoinService teamJoinService;
    private final ProjectSearchService projectSearchService;
    private final ProjectInfoService projectInfoService;
    private final MyProjectService myProjectService;
    @Autowired
    TokenProvider tokenProvider;

    private long CheckToken(HttpServletRequest token) {
        var getToken = token.getHeader("Authorization");
        String jwtToken = getToken.replace("Bearer ", "");
        long loginId;
        try {
            loginId = Long.parseLong(tokenProvider.validateJwt(jwtToken));
            System.out.println("user id: " + loginId);
        } catch (Exception e) {
            throw new TempHandler(ErrorStatus._UNAUTHORIZED);
        }
        return loginId;
    }

    @PostMapping("/create")
    public ApiResponse<ProjectResponseDto.JoinProjectCreateResponseDto> join(HttpServletRequest token, @RequestBody @Valid ProjectRequestDto.JoinProjectCreateRequestDto request) {

        //토큰 검증
        CheckToken(token);
        Project project = projectCreateService.joinProject(request);
        return ApiResponse.onSuccess(ProjectConverter.toProjectDto(project));
    }

    @PostMapping("/team/{id}")
    public ApiResponse<ProjectResponseDto.JoinTeamResponseDto> joinTeam(HttpServletRequest token, @PathVariable("id") long id, @RequestBody @Valid ProjectRequestDto.JoinTeamRequestDto request) {
        //토큰 검증
        CheckToken(token);
        return ApiResponse.onSuccess(TeamConverter.toTeamDto(teamJoinService.joinTeam(request, id)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam(required = false) String name) {

        List<Project> projects;
        if (name != null) {
            projects = projectSearchService.searchProjectsByName(name);
        } else {
            projects = projectSearchService.searchAllProjects();
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<ApiResponse<Project>> getProjectInfo(@PathVariable long id) {
        Project project = projectInfoService.projectInfo(id);
        ApiResponse<Project> response = new ApiResponse<>(true, "200", "Project retrieved successfully", project);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProjectResponseDto.MyProjectResponseDto>> getUserProjects(HttpServletRequest token) {
        // 토큰에서 "Bearer " 부분 제거
        var getToken = token.getHeader("Authorization");
        String jwtToken = getToken.replace("Bearer ", "");
        List<ProjectResponseDto.MyProjectResponseDto> projects = myProjectService.getProjectsByUserToken(jwtToken);
        return ResponseEntity.ok(projects);
    }
}