package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Assignee;
import com.issuestation.Entity.Fixer;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.Reporter;
import com.issuestation.Entity.enums.Status;
import com.issuestation.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class IssueSearchService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    private IssueRepository issueRepository;
    private final AssigneeRepository assigneeRepository;
    private final ReporterRepository reporterRepository;
    private final FixerRepository fixerRepository;
    private final UserRepository userRepository;

    public IssueSearchService( AssigneeRepository assigneeRepository, ReporterRepository reporterRepository, UserRepository userRepository, FixerRepository fixerRepository) {
        this.assigneeRepository = assigneeRepository;
        this.reporterRepository = reporterRepository;
        this.userRepository = userRepository;
        this.fixerRepository = fixerRepository;
    }
    public List<IssueResponseDto.JoinIssueSearchResponseDto> searchIssuesByProjectIdNameAndStatus(Long projectId, String name, String status) {
        if (Objects.equals(status, "")) {
            List<Issue> issues = issueRepository.findByProjectIdAndNameContaining(projectId, name);
            return issues.stream().map(this::convertToDto).collect(Collectors.toList());
        } else {

            Status statusEnum = Status.valueOf(status);
            List<Issue> issues = issueRepository.findByProjectIdAndNameContainingAndStatus(projectId, name, statusEnum);
            return issues.stream().map(this::convertToDto).collect(Collectors.toList());
        }
    }

    public List<IssueResponseDto.JoinIssueSearchResponseDto> searchIssuesByProjectId(Long projectId) {
        List<Issue> issues = issueRepository.findByProjectId(projectId);
        return issues.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private IssueResponseDto.JoinIssueSearchResponseDto convertToDto(Issue issue) {

        Assignee assigneeId = assigneeRepository.findDistinctFirstByIssueId(issue.getId()).orElse(null);

        Reporter reporterId = reporterRepository.findDistinctFirstByIssueId(issue.getId()).orElse(null);

        Fixer fixerId = fixerRepository.findDistinctFirstByIssueId(issue.getId()).orElse(null);

        String assigneeNickname = "not assigned";
        String reporterNickname = "not reported";
        String fixerNickname = "not fixed";

        if (assigneeId != null) {
            assigneeNickname = userRepository.findById(Objects.requireNonNull(assigneeId).getUser().getId()).get().getNickname();
        }
        if (reporterId != null) {
            reporterNickname = userRepository.findById(Objects.requireNonNull(reporterId).getUser().getId()).get().getNickname();
        }

        if (fixerId != null) {
            fixerNickname = userRepository.findById(Objects.requireNonNull(fixerId).getUser().getId()).get().getNickname();
        }

        return IssueResponseDto.JoinIssueSearchResponseDto.builder()
                .id(issue.getId())
                .name(issue.getName())
                .description(issue.getDescription())
                .status(issue.getStatus())
                .priority(issue.getPriority())
                .assignee(assigneeNickname)
                .reporter(reporterNickname)
                .fixer(fixerNickname)
                .projectId(issue.getProject().getId())
                .initDate(String.valueOf(issue.getInitdate()))
                .modDate(String.valueOf(issue.getModdate()))
                .build();
    }
}
