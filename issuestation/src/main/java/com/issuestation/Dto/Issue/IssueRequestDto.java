package com.issuestation.Dto.Issue;

import com.issuestation.Entity.enums.CommentTag;
import com.issuestation.Entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class IssueRequestDto {

    @Getter
    public static class JoinIssueCreateRequestDto {
        String name;
        String description;
    }

    @Getter
    public static class JoinIssueStateRequestDto {
        private Status status;
    }

    @Getter
    public static class JoinIssueSearchRequestDto {
        private String name;
        private Status status;
    }

    @Getter
    public static class JoinIssueModifyRequestDto {
        String name;
        String description;
    }

    @Getter
    public static class JoinCommentCreateRequestDto {
        String comment;
        CommentTag tag;
    }

    @Getter
    @Setter
    public static class JoinAssigneeRequestDto {
        private String nickname;
    }
}
