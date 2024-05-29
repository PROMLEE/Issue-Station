package com.issuestation.Dto.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ProjectResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinProjectCreateResponseDto {
        Long id;
//        String thumbnaillink;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinTeamResponseDto {
        Long id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectInfoResponseDto {
        Long id;
        String name;
        String description;
        Boolean isPrivate;
        String thumbnaillink;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyProjectResponseDto {
        private Long id;
        private String name;
        private Boolean isPrivate;
        private String description;
        private String thumbnaillink;
        private LocalDateTime initdate;
        private LocalDateTime moddate;
    }
}
