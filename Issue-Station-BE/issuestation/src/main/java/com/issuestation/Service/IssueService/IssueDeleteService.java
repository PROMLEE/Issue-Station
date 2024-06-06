package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Entity.Issue;
import org.springframework.transaction.annotation.Transactional;

public interface IssueDeleteService {
    @Transactional
    void deleteIssue(long issueId);
}
