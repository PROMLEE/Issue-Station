package com.issuestation.Service.IssueService;

import com.issuestation.Dto.Issue.IssueRequestDto;
import com.issuestation.Entity.Comment;
import com.issuestation.Entity.Issue;

public interface CommentCreateService {

    Comment createComment(IssueRequestDto.JoinCommentCreateRequestDto request, long issueId, String token);

}
