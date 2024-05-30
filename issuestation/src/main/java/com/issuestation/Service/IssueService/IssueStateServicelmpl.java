package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Repository.IssueRepository;
import com.issuestation.converter.IssueCreateConverter;
import com.issuestation.converter.IssueStateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueStateServicelmpl implements IssueStateService {

    private final IssueRepository issueRepository;

    @Override
    @Transactional
    public Issue joinIssueState(IssueRequestDto.JoinIssueStateRequestDto requset) {

        Issue newIssue= IssueStateConverter.toIssueEntity(requset);
        return issueRepository.save(newIssue);
    }
}
