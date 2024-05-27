//package com.issuestation.Controller;
//
//import com.issuestation.Dto.ResponseDto;
//import com.issuestation.Security.TokenProvider;
//import com.issuestation.Service.Temp.ProjectService;
//import com.issuestation.Dto.ProjectCreateRequestDto;
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
//    public ResponseDto<?> createProject(@RequestHeader("Authorization") String token, @RequestBody ProjectCreateRequestDto projectCreateRequestDto) {
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

import com.issuestation.Dto.ProjectCreateRequestDto;
import com.issuestation.Dto.ProjectCreateResponseDto;
import com.issuestation.Entity.ProjectEntity;
import com.issuestation.Service.ProjectService.ProjectCreateCommandService;
import com.issuestation.apiPayload.ApiResponse;
import com.issuestation.converter.ProjectCreateConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectCreateCommandService projectService;

    @PostMapping("/create")
    public ApiResponse<ProjectCreateResponseDto.JoinProjectCreateResponseDto> join(@RequestBody @Valid ProjectCreateRequestDto.JoinProjectCreateRequestDto request) {
        ProjectEntity project = projectService.joinProject(request);
        return ApiResponse.onSuccess(ProjectCreateConverter.toJoinProjectCreateResponseDto(project));
    }

}