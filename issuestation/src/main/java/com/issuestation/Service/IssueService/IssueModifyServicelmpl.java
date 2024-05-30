package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Repository.IssueRepository;
import com.issuestation.converter.IssueModifyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueModifyServicelmpl implements IssueModifyService {

    private final IssueRepository issueRepository;

    @Override
    @Transactional
    public Issue joinIssueModify(IssueRequestDto.JoinIssueModifyRequestDto requset) {

        Issue newIssue= IssueModifyConverter.toIssueEntity(requset);
        return issueRepository.save(newIssue);
    }
}
