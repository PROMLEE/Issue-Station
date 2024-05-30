package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Comment;
import com.issuestation.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentListService {
    private final CommentRepository commentRepository;

    public List<IssueResponseDto.GetCommentResponseDto> getCommentsByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId).stream().map(comment -> IssueResponseDto.GetCommentResponseDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .tag(comment.getTag())
                .nickname(comment.getUser().getNickname())
                .modDate(String.valueOf(comment.getModdate()))
                .build()).toList();

    }
}
