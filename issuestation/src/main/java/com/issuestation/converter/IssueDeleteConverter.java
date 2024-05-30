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
    public static Issue toIssueEntity(IssueRequestDto.JoinIssueDeleteRequestDto request) {
       return Issue.builder() // 원칙적으로 바디에 아무것도 없으나 어떤 id의 이슈가 삭제되었는지 반환...맞나?
               .id(request.getId())
               .build();
    }
}