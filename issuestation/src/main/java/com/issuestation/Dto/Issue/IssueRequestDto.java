package com.issuestation.Dto.Issue;

import lombok.Getter;

public class IssueRequestDto {

    @Getter
    public static class JoinIssueCreateRequestDto {
        String name;
        String description;
    }

}
