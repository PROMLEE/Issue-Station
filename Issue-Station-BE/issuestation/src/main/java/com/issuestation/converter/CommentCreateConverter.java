package com.issuestation.converter;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Dto.Issue.IssueResponseDto;
import com.issuestation.Entity.Comment;
import com.issuestation.Entity.Issue;
import com.issuestation.Entity.User;
import com.issuestation.Entity.enums.CommentTag;

public class CommentCreateConverter {
    public static IssueResponseDto.JoinCommentCreateResponseDto toCommentDto(Comment comment) {
        return IssueResponseDto.JoinCommentCreateResponseDto.builder()
                .id(comment.getId())
                .build();
    }

    public static Comment toCommentEntity(IssueRequestDto.JoinCommentCreateRequestDto request, Issue issue, User userId) {
        return Comment.builder()
                .comment(request.getComment())
                .tag(request.getTag())
                .issue(issue)
                .user(userId)
                .build();
    }
}
