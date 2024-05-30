package com.issuestation.converter;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Issue;

public class IssueCreateConverter {
    public static IssueResponseDto.JoinIssueCreateResponseDto toIssueDto(Issue issue) {
        return IssueResponseDto.JoinIssueCreateResponseDto.builder()
                .id(issue.getId())
                .build();
    }

    public static Issue toIssueEntity(IssueRequestDto.JoinIssueCreateRequestDto request) {
        return Issue.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}