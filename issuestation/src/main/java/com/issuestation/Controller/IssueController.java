package com.issuestation.Controller;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.Project;
import com.issuestation.Security.TokenProvider;
import com.issuestation.Service.IssueService.IssueCreateService;
import com.issuestation.Service.IssueService.IssueStateService;
import com.issuestation.apiPayload.ApiResponse;
import com.issuestation.apiPayload.code.status.ErrorStatus;
import com.issuestation.apiPayload.exception.handler.TempHandler;
import com.issuestation.converter.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issue")
public class IssueController {

    private final IssueCreateService issueCreateService;
    private final IssueStateService issueStateService;

    @Autowired
    TokenProvider tokenProvider;

    private long checkToken(HttpServletRequest token) {
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

    @PostMapping("/create/{id}")
    public ApiResponse<IssueResponseDto.JoinIssueCreateResponseDto> join(HttpServletRequest token, @PathVariable("id") long projectId, @RequestBody @Valid IssueRequestDto.JoinIssueCreateRequestDto request) {

        //토큰 검증
        checkToken(token);

        Issue issue = issueCreateService.joinIssue(request, projectId);
        return ApiResponse.onSuccess(IssueCreateConverter.toIssueDto(issue));
    }

//    private final IssueCreateService issueCreateService;
//    private final IssueStateService issueStateService;
//    private final IssueModifyService issueModifyService;
//    private final IssueDeleteService issueDeleteService;
//
//    @PostMapping("/create")
//    public ApiResponse<IssueResponseDto.JoinIssueCreateResponseDto> join(@RequestBody @Valid IssueRequestDto.JoinIssueCreateRequestDto request) {
//        Issue issue = issueCreateService.joinIssue(request);
//        return ApiResponse.onSuccess(IssueCreateConverter.toIssueDto(issue));
//    }
//    @PostMapping("/state")
//    public ApiResponse<IssueResponseDto.JoinIssueStateResponseDto> join(@RequestBody @Valid IssueRequestDto.JoinIssueStateRequestDto request) {
//        Issue issue = issueStateService.joinIssueState(request);
//        return ApiResponse.onSuccess(IssueStateConverter.toIssueDto(issue));
//    }
//
//    @PutMapping("/modify")
//    public ApiResponse<IssueResponseDto.JoinIssueModifyResponseDto> join(@RequestBody @Valid IssueRequestDto.JoinIssueModifyRequestDto request) {
//        Issue issue = issueModifyService.joinIssueModify(request);
//        return ApiResponse.onSuccess(IssueModifyConverter.toIssueDto(issue));
//    }
//    @DeleteMapping("/delete")
//    public ApiResponse<IssueResponseDto.JoinIssueDeleteResponseDto> join(@RequestBody @Valid IssueRequestDto.JoinIssueDeleteRequestDto request) {
//        Issue issue = issueDeleteService.joinIssueDelete(request);
//        return ApiResponse.onSuccess(IssueDeleteConverter.toIssueDto(issue));
//    }


}




