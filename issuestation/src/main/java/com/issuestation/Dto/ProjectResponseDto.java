package com.issuestation.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProjectResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinProjectCreateResponseDto{
        Integer pid;
//        String thumbnaillink;
    }
}
