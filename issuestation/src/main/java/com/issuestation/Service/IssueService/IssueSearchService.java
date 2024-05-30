package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.enums.Status;
import com.issuestation.Repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueSearchService {

    @Autowired
    private IssueRepository issueRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public List<IssueResponseDto.JoinIssueSearchResponseDto> searchIssuesByProjectIdNameAndStatus(Long projectId, String name, Status status) {
        List<Issue> issues = issueRepository.findByProjectIdAndNameAndStatus(projectId, name, status);
        return issues.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<IssueResponseDto.JoinIssueSearchResponseDto> searchIssuesByProjectId(Long projectId) {
        List<Issue> issues = issueRepository.findByProjectId(projectId);
        return issues.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private IssueResponseDto.JoinIssueSearchResponseDto convertToDto(Issue issue) {
        return IssueResponseDto.JoinIssueSearchResponseDto.builder()
                .id(issue.getId())
                .name(issue.getName())
                .description(issue.getDescription())
                .status(issue.getStatus())
                .projectId(issue.getProject().getId())
                .modDate(String.valueOf(issue.getModdate()))
                .build();
    }
}
