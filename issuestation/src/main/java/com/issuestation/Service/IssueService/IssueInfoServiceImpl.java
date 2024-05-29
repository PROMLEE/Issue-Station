package com.issuestation.Service.IssueService;

import com.issuestation.Entity.Issue;
import com.issuestation.Repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueInfoServiceImpl implements IssueInfoService {
    private final IssueRepository issueRepository;

    @Override
    public Issue infoIssue(long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid issue ID"));
    }
}
