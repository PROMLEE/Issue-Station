package com.issuestation.converter;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Issue;

public class IssueStateConverter {
    public static IssueResponseDto.JoinIssueStateResponseDto toIssueDto(Issue issue) {

        return IssueResponseDto.JoinIssueStateResponseDto.builder()
                .status(issue.getStatus())
                .build();
    }

    public static Issue toIssueEntity(IssueRequestDto.JoinIssueStateRequestDto request) {
        return Issue.builder()
                .status(request.getStatus())
                .build();
    }
}