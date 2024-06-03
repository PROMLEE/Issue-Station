package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Assignee;
import com.issuestation.Entity.Fixer;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.Reporter;
import com.issuestation.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IssueInfoServiceImpl {

    private final IssueRepository issueRepository;
    private final AssigneeRepository assigneeRepository;
    private final ReporterRepository reporterRepository;
    private final FixerRepository fixerRepository;
    private final UserRepository userRepository;

    public IssueInfoServiceImpl(IssueRepository issueRepository, AssigneeRepository assigneeRepository, ReporterRepository reporterRepository, UserRepository userRepository, FixerRepository fixerRepository) {
        this.issueRepository = issueRepository;
        this.assigneeRepository = assigneeRepository;
        this.reporterRepository = reporterRepository;
        this.userRepository = userRepository;
        this.fixerRepository = fixerRepository;
    }

    @Transactional(readOnly = true)
    public IssueResponseDto.JoinIssueInfoResponseDto getIssueDetails(Long issueId) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));

        Assignee assigneeId = assigneeRepository.findDistinctFirstByIssueId(issueId).orElse(null);

        Reporter reporterId = reporterRepository.findDistinctFirstByIssueId(issueId).orElse(null);

        Fixer fixerId = fixerRepository.findDistinctFirstByIssueId(issueId).orElse(null);

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

        return new IssueResponseDto.JoinIssueInfoResponseDto(
                issue.getId(),
                issue.getName(),
                issue.getDescription(),
                issue.getStatus(),
                issue.getPriority(),
                issue.getProject().getId(),
                String.valueOf(issue.getInitdate()),
                String.valueOf(issue.getModdate()),
                assigneeNickname,
                reporterNickname,
                        fixerNickname
        );
    }
}
