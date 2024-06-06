package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Entity.Comment;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.User;
import com.issuestation.Repository.CommentRepository;
import com.issuestation.Repository.IssueRepository;
import com.issuestation.Repository.UserRepository;
import com.issuestation.Security.TokenProvider;
import com.issuestation.converter.CommentCreateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentCreateServiceImpl implements CommentCreateService{

    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Comment createComment(IssueRequestDto.JoinCommentCreateRequestDto request, long issueId, String token) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(()-> new IllegalArgumentException("Invalid Issue ID"));
        long userId = Long.parseLong(tokenProvider.validateJwt(token));
        User userEntity = userRepository.findById(userId).get();
        Comment comment = CommentCreateConverter.toCommentEntity(request, issue, userEntity);
        return commentRepository.save(comment);
    }
}
