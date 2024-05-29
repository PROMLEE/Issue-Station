//package com.issuestation.Controller;
//
//import com.issuestation.Dto.ResponseDto;
//import com.issuestation.Security.TokenProvider;
//import com.issuestation.Service.Temp.ProjectService;
//import com.issuestation.Dto.Project.ProjectRequestDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/project")
//public class ProjectController {
//
//    @Autowired private ProjectService projectService; //서비스는 db랑 상호작용하기위해 필요함
//    @Autowired private TokenProvider tokenProvider; //토큰 가져와야함 여기서
//
//    @PostMapping("/create")
//    public ResponseDto<?> createProject(@RequestHeader("Authorization") String token, @RequestBody ProjectRequestDto projectCreateRequestDto) {
//        //Bearer 토큰에서 값 추출
//        String jwtToken = token.replace("Bearer ", "");
//
//        //토큰 검증
//        String userId;
//        try {
//            userId = tokenProvider.validateJwt(jwtToken);
//        } catch (Exception e) {
//            return ResponseDto.setFailed("Invalid token", null);
//        }
//
//        //토큰이 유효할 경우에만 프젝생성 가능하도록
//        ResponseDto<?> result = projectService.createProject(projectCreateRequestDto, userId);
//        System.out.println(result.toString());
//        return result;
//    }
//}
package com.issuestation.Controller;

import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Entity.Project;
import com.issuestation.Security.TokenProvider;
import com.issuestation.Service.ProjectService.ProjectCreateService;
import com.issuestation.Service.ProjectService.ProjectSearchService;
import com.issuestation.Service.ProjectService.TeamJoinService;
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
    @Autowired  TokenProvider tokenProvider;

    @PostMapping("/create")
    public ApiResponse<ProjectResponseDto.JoinProjectCreateResponseDto> join(HttpServletRequest token, @RequestBody @Valid ProjectRequestDto.JoinProjectCreateRequestDto request) {

        //토큰 검증
        var getToken = token.getHeader("Authorization");
        String jwtToken = getToken.replace("Bearer ", "");
        long loginId;
        try {
            loginId = Long.parseLong(tokenProvider.validateJwt(jwtToken));
            System.out.println("user id: "+loginId);
        } catch (Exception e) {
            throw new TempHandler(ErrorStatus._UNAUTHORIZED);
        }
        Project project = projectCreateService.joinProject(request);
        return ApiResponse.onSuccess(ProjectConverter.toProjectDto(project));
    }

    @PostMapping("/team/{id}")
    public ApiResponse<ProjectResponseDto.JoinTeamResponseDto> joinTeam(HttpServletRequest token, @PathVariable("id") long id, @RequestBody @Valid ProjectRequestDto.JoinTeamRequestDto request) {
        //토큰 검증
        var getToken = token.getHeader("Authorization");
        String jwtToken = getToken.replace("Bearer ", "");
        long loginId;
        try {
            loginId = Long.parseLong(tokenProvider.validateJwt(jwtToken));
            System.out.println("user id: "+loginId);
        } catch (Exception e) {
            throw new TempHandler(ErrorStatus._UNAUTHORIZED);
        }
        return ApiResponse.onSuccess(TeamConverter.toTeamDto(teamJoinService.joinTeam(request, id)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String name) {

        List<Project> projects;

        if (name != null) {
            projects = projectSearchService.searchProjectsByName(name);
        }  else {
            projects = projectSearchService.searchAllProjects();
        }

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}