package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.*;
import com.issuestation.Entity.enums.CommentTag;
import com.issuestation.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SetAssigneeService {
    private final AssigneeRepository assigneeRepository;
    private final ReporterRepository reportRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final FixerRepository fixerRepository;

    @Autowired
    public SetAssigneeService(AssigneeRepository assigneeRepository, IssueRepository issueRepository, UserRepository userRepository, ReporterRepository reportRepository, FixerRepository fixerRepository) {
        this.assigneeRepository = assigneeRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.fixerRepository = fixerRepository;
    }

    public Assignee assignAssignee(Long issueId, IssueRequestDto.JoinAssigneeRequestDto assigneeRequest) {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findByNickname(assigneeRequest.getNickname());

        if (issueOptional.isPresent() && userOptional.isPresent()) {
            Issue issue = issueOptional.get();
            User user = userOptional.get();

            Assignee assignee = Assignee.builder()
                    .issue(issue)
                    .user(user)
                    .build();
            return assigneeRepository.save(assignee);
        } else {
            throw new RuntimeException("Issue or User not found");
        }
    }

    public Reporter assignReporter(Long issueId, IssueRequestDto.JoinAssigneeRequestDto assigneeRequest) {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findByNickname(assigneeRequest.getNickname());

        if (issueOptional.isPresent() && userOptional.isPresent()) {
            Issue issue = issueOptional.get();
            User user = userOptional.get();

            Reporter reporter = Reporter.builder()
                    .issue(issue)
                    .user(user)
                    .build();
            return reportRepository.save(reporter);
        } else {
            throw new RuntimeException("Issue or User not found");
        }
    }

    public Fixer assignFixer(Long issueId, IssueRequestDto.JoinAssigneeRequestDto assigneeRequest) {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findByNickname(assigneeRequest.getNickname());

        if (issueOptional.isPresent() && userOptional.isPresent()) {
            Issue issue = issueOptional.get();
            User user = userOptional.get();

            Fixer fixer = Fixer.builder()
                    .issue(issue)
                    .user(user)
                    .build();
            return fixerRepository.save(fixer);
        } else {
            throw new RuntimeException("Issue or User not found");
        }
    }
}
