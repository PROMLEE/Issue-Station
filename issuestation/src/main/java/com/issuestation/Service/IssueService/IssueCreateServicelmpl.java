package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Repository.IssueRepository;
import com.issuestation.converter.IssueCreateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueCreateServicelmpl implements IssueCreateService {

    private final IssueRepository issueRepository;

    @Override
    @Transactional
    public Issue joinIssue(IssueRequestDto.JoinIssueCreateRequestDto requset) {

        Issue newIssue= IssueCreateConverter.toIssueEntity(requset);
        return issueRepository.save(newIssue);
    }
}
