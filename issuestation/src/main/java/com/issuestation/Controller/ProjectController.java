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
import com.issuestation.apiPayload.ApiResponse;
import com.issuestation.apiPayload.code.status.ErrorStatus;
import com.issuestation.apiPayload.exception.handler.TempHandler;
import com.issuestation.converter.ProjectCreateConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectCreateService projectService;
    @Autowired  private TokenProvider tokenProvider;

    @PostMapping("/create")
    public ApiResponse<ProjectResponseDto.JoinProjectCreateResponseDto> join(@RequestHeader("Authorization") String token, @RequestBody @Valid ProjectRequestDto.JoinProjectCreateRequestDto request) {
        String jwtToken = token.replace("Bearer ", "");

        //토큰 검증
        String loginId;
        try {
            loginId = tokenProvider.validateJwt(jwtToken);
        } catch (Exception e) {
            throw new TempHandler(ErrorStatus.TEMP_EXCEPTION);
        }
        Project project = projectService.joinProject(request);
        return ApiResponse.onSuccess(ProjectCreateConverter.toProjectDto(project));
    }

}
//package com.cau.issuemanagement.issuestation.Controller;
//
//import com.cau.issuemanagement.issuestation.Dto.ProjectCreateDto;
//import com.cau.issuemanagement.issuestation.Dto.ResponseDto;
//import com.cau.issuemanagement.issuestation.Security.TokenProvider;
//import com.cau.issuemanagement.issuestation.Service.ProjectService;
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
//    public ResponseDto<?> createProject(@RequestHeader("Authorization") String token, @RequestBody ProjectCreateDto projectCreateDto) {
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
//        ResponseDto<?> result = projectService.createProject(projectCreateDto, userId);
//        System.out.println(result.toString());
//        return result;
//    }
//}