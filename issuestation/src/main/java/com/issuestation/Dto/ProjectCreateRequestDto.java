package com.issuestation.Dto;

import lombok.Getter;

public class ProjectCreateRequestDto {

    @Getter
    public static class JoinProjectCreateRequestDto {
        String pname;
        String description;
        Boolean isprivate;
//        String thumbnaillink;
    }
}
