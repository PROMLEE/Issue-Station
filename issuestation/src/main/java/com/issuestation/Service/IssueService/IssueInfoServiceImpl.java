package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Repository.AssigneeRepository;
import com.issuestation.Repository.IssueRepository;
import com.issuestation.Repository.ReporterRepository;
import com.issuestation.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IssueInfoServiceImpl {

    private final IssueRepository issueRepository;
    private final AssigneeRepository assigneeRepository;
    private final ReporterRepository reporterRepository;
    private final UserRepository userRepository;

    public IssueInfoServiceImpl(IssueRepository issueRepository, AssigneeRepository assigneeRepository, ReporterRepository reporterRepository, UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.assigneeRepository = assigneeRepository;
        this.reporterRepository = reporterRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public IssueResponseDto.JoinIssueInfoResponseDto getIssueDetails(Long issueId) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));

        Long assigneeId = assigneeRepository.findDistinctFirstByIssueId(issueId).getId();

        Long reporterId = reporterRepository.findDistinctFirstByIssueId(issueId).getId();

        String assigneeNickname = userRepository.findById(assigneeId).get().getNickname();

        String reporterNickname = userRepository.findById(reporterId).get().getNickname();

        return new IssueResponseDto.JoinIssueInfoResponseDto(
                issue.getId(),
                issue.getName(),
                issue.getDescription(),
                issue.getStatus(),
                issue.getProject().getId(),
                String.valueOf(issue.getInitdate()),
                String.valueOf(issue.getInitdate()),
                assigneeNickname,
                reporterNickname
        );
    }
}
