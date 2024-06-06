package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.Project;
import com.issuestation.Repository.IssueRepository;
import com.issuestation.Repository.ProjectRepository;
import com.issuestation.converter.IssueCreateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueCreateServicelmpl implements IssueCreateService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public Issue joinIssue(IssueRequestDto.JoinIssueCreateRequestDto requset, long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new IllegalArgumentException("Invalid project ID"));
        Issue newIssue= IssueCreateConverter.toIssueEntity(requset, project);
        return issueRepository.save(newIssue);
    }
}
