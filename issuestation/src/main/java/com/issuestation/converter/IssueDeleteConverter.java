package com.issuestation.converter;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Issue;

public class IssueDeleteConverter {
    public static IssueResponseDto.JoinIssueDeleteResponseDto toIssueDto(Issue issue) {
        return IssueResponseDto.JoinIssueDeleteResponseDto.builder()
                .id(issue.getId())
                .build();
    }
}