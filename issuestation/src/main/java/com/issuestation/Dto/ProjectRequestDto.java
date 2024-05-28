package com.issuestation.Dto;

import lombok.Getter;

public class ProjectRequestDto {

    @Getter
    public static class JoinProjectCreateRequestDto {
        String pname;
        String description;
        Boolean isPrivate;
//        String thumbnaillink;
    }

}
