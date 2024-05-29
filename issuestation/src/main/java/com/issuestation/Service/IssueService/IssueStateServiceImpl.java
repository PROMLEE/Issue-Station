package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Repository.IssueRepository;
import com.issuestation.converter.IssueStateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueStateServiceImpl implements IssueStateService {

    private final IssueRepository issueRepository;

    @Override
    @Transactional
    public Issue changeIssueState(IssueRequestDto.JoinIssueStateRequestDto request, long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found"));

        Issue updatedIssue = IssueStateConverter.updateIssueState(issue, request.getStatus());
        return issueRepository.save(updatedIssue);
    }
}
