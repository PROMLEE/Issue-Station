package com.issuestation.Dto.Project;

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
