package com.issuestation.Dto.Issue;

import com.issuestation.Entity.enums.Status;
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
        Status status;
    }
    @Getter
    public static class JoinIssueModifyRequestDto {
        String name;
        String description;
    }

}
