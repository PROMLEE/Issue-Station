package com.issuestation.converter;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.enums.Status;

public class IssueStateConverter {


    public static Issue updateIssueState(Issue issue, Status newStatus) {
        return Issue.builder()
                .id(issue.getId())
                .description(issue.getDescription())
                .name(issue.getName())
                .project(issue.getProject())
                .status(newStatus)
                .build();
    }


    public static IssueResponseDto.JoinIssueStateResponseDto toIssueDto(Issue issue) {
        return IssueResponseDto.JoinIssueStateResponseDto.builder()
                .status(issue.getStatus())
                .build();
    }
}