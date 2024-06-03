package com.issuestation.Dto.Issue;

import com.issuestation.Entity.enums.CommentTag;
import com.issuestation.Entity.enums.Priority;
import com.issuestation.Entity.enums.Status;
import lombok.*;

import java.util.Date;

public class IssueResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinIssueCreateResponseDto {
        Long id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinIssueModifyResponseDto {
        Long id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinIssueDeleteResponseDto {
        Long id; //삭제된 Id는 반환해주니까 냅둠
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinIssueStateResponseDto {
        Status status;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinIssueInfoResponseDto {
        Long id;
        String name;
        String description;
        Status status;
        Priority priority;
        Long projectId;
        String initDate;
        String modDate;
        String assignee;
        String reporter;
        String fixer;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinIssueSearchResponseDto {
        Long id;
        String name;
        String description;
        Status status;
        Priority priority;
        String reporter;
        String assignee;
        String fixer;
        Long projectId;
        String modDate;
        String initDate;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinCommentCreateResponseDto {
        Long id; //작성된 코멘트 아이디 반환
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinAssigneeCreateResponseDto {
        //        private Long id;
        //        private Long userId;
        private String nickname;
//        private Long issueId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCommentResponseDto {
        private Long id;
        private String comment;
        private CommentTag tag;
        private String nickname;
        private String modDate;
    }

}
