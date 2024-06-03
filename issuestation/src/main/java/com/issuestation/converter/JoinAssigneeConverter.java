package com.issuestation.converter;

import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Assignee;
import com.issuestation.Entity.Fixer;
import com.issuestation.Entity.Reporter;

public class JoinAssigneeConverter {

    public static IssueResponseDto.JoinAssigneeCreateResponseDto toIssueDto(Assignee assignee) {
        return IssueResponseDto.JoinAssigneeCreateResponseDto.builder()
                .nickname(assignee.getUser().getNickname())
                .build();
    }

    public static IssueResponseDto.JoinAssigneeCreateResponseDto toReporterDto(Reporter reporter) {
        return IssueResponseDto.JoinAssigneeCreateResponseDto.builder()
                .nickname(reporter.getUser().getNickname())
                .build();
    }

    public static IssueResponseDto.JoinAssigneeCreateResponseDto toFixerDto(Fixer fixer) {
        return IssueResponseDto.JoinAssigneeCreateResponseDto.builder()
                .nickname(fixer.getUser().getNickname())
                .build();
    }
}
