package com.issuestation.converter;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.Project;
import com.issuestation.Entity.enums.Priority;
import com.issuestation.Entity.enums.Status;
import com.issuestation.Repository.ProjectRepository;
import com.issuestation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class IssueCreateConverter {

    public static IssueResponseDto.JoinIssueCreateResponseDto toIssueDto(Issue issue) {
        return IssueResponseDto.JoinIssueCreateResponseDto.builder()
                .id(issue.getId())
                .build();
    }

    public static Issue toIssueEntity(IssueRequestDto.JoinIssueCreateRequestDto request, Project project) {
        return Issue.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(Status.NEW) // 기본 상태는 new
                .priority(Optional.ofNullable(request.getPriority()).orElse(Priority.MAJOR)) // priority가 null일 때 기본값 설정
                .project(project)
                .build();
    }

}