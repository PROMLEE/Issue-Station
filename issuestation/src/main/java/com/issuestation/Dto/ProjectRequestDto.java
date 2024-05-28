package com.issuestation.Dto;

import lombok.Getter;

public class ProjectRequestDto {

    @Getter
    public static class JoinProjectCreateRequestDto {
        String name;
        String description;
        Boolean isPrivate;
//        String thumbnaillink;
    }

}
