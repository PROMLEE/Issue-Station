package com.issuestation.Controller;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Service.IssueService.IssueCreateService;
import com.issuestation.Service.IssueService.IssueStateService;
import com.issuestation.apiPayload.ApiResponse;
import com.issuestation.converter.IssueCreateConverter;
import com.issuestation.converter.IssueStateConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issue")
public class IssueController {
    private final IssueCreateService issueCreateService;
    private final IssueStateService issueStateService;

    @PostMapping("/create")
    public ApiResponse<IssueResponseDto.JoinIssueCreateResponseDto> join(@RequestBody @Valid IssueRequestDto.JoinIssueCreateRequestDto request) {
        Issue issue = issueCreateService.joinIssue(request);
        return ApiResponse.onSuccess(IssueCreateConverter.toIssueDto(issue));
    }
    @PostMapping("/state")
    public ApiResponse<IssueResponseDto.JoinIssueStateResponseDto> join(@RequestBody @Valid IssueRequestDto.JoinIssueStateRequestDto request) {
        Issue issue = issueStateService.joinIssueState(request);
        return ApiResponse.onSuccess(IssueStateConverter.toIssueDto(issue));
    }
}




