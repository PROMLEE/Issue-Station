package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.Project;

public interface IssueCreateService {

    Issue joinIssue(IssueRequestDto.JoinIssueCreateRequestDto request, long projectId);

}
