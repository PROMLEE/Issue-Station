package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Entity.Issue;
import com.issuestation.Repository.IssueRepository;
import com.issuestation.converter.IssueCreateConverter;
import com.issuestation.converter.IssueDeleteConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueDeleteServicelmpl implements IssueDeleteService {

    private final IssueRepository issueRepository;

    @Override
    @Transactional
    public Issue joinIssueDelete(IssueRequestDto.JoinIssueDeleteRequestDto requset) {

        Issue newIssue= IssueDeleteConverter.toIssueEntity(requset);
        return issueRepository.save(newIssue); // delete..? 사용.. 모르겠음 ㅠ
    }
}
