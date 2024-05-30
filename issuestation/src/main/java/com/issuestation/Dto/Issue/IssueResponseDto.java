package com.issuestation.Dto.Issue;

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
        Long projectId;
        String initDate;
        String modDate;
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
        Long projectId;
        String modDate;
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

}
