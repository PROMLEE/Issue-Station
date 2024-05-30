package com.issuestation.converter;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Issue;

public class IssueModifyConverter {
    public static IssueResponseDto.JoinIssueModifyResponseDto toIssueDto(Issue issue) {
        return IssueResponseDto.JoinIssueModifyResponseDto.builder()
                .id(issue.getId())
                .build();
    }

    public static Issue toIssueEntity(IssueRequestDto.JoinIssueModifyRequestDto request) {
        return Issue.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}