package com.issuestation.Dto.Issue;

import com.issuestation.Entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class IssueResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinIssueCreateResponseDto{
        Long id;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinIssueModifyResponseDto{
        Long id;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinIssueDeleteResponseDto{
        Long id;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinIssueStateResponseDto{
        Status status;
    }
}
