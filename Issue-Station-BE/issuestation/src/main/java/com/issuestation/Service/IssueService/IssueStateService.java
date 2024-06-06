package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Entity.Issue;

public interface IssueStateService {
    void changeIssueState(IssueRequestDto.JoinIssueStateRequestDto request, long issueId);
}
