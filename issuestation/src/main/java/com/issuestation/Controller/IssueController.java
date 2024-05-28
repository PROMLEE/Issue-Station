package com.issuestation.Controller;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Service.IssueService.IssueCreateService;
import com.issuestation.Service.IssueService.IssueModifyService;
import com.issuestation.Service.IssueService.IssueStateService;
import com.issuestation.apiPayload.ApiResponse;
import com.issuestation.converter.IssueCreateConverter;
import com.issuestation.converter.IssueModifyConverter;
import com.issuestation.converter.IssueStateConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issue")
public class IssueController {
    private final IssueCreateService issueCreateService;
    private final IssueStateService issueStateService;
    private final IssueModifyService issueModifyService;

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

    @PutMapping("/modify")
    public ApiResponse<IssueResponseDto.JoinIssueModifyResponseDto> join(@RequestBody @Valid IssueRequestDto.JoinIssueModifyRequestDto request) {
        Issue issue = issueModifyService.joinIssueModify(request);
        return ApiResponse.onSuccess(IssueModifyConverter.toIssueDto(issue));
    }


}




