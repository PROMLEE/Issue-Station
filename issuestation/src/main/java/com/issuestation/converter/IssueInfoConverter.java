package com.issuestation.converter;

import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Assignee;
import com.issuestation.Entity.Issue;

import java.time.ZoneId;
import java.util.Date;

public class IssueInfoConverter {


//    public static IssueResponseDto.JoinIssueInfoResponseDto toIssueDto(Issue issue) {
//
//        Assignee assignee = issue.getAssignee();
//
//        return IssueResponseDto.JoinIssueInfoResponseDto.builder()
//                .id(issue.getId())
//                .name(issue.getName())
//                .description(issue.getDescription())
//                .status(issue.getStatus())
//                .projectId(issue.getProject().getId())
//                .initDate(String.valueOf(issue.getInitdate()))
//                .modDate(String.valueOf(issue.getModdate()))
//                .assignee(issue.getAssignee().getUsername())
//                .reporter(issue.getReporter().getUsername())
//                .build();
//    }
}
