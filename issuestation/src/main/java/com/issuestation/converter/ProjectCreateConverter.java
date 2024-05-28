package com.issuestation.converter;

import com.issuestation.Dto.ProjectRequestDto;
import com.issuestation.Dto.ProjectResponseDto;
import com.issuestation.Entity.Project;

public class ProjectCreateConverter {
    public static ProjectResponseDto.JoinProjectCreateResponseDto toProjectDto(Project project) {
        return ProjectResponseDto.JoinProjectCreateResponseDto.builder()
//                .pid(project.getId())
                .build();
    }

    public static Project toProjectEntity(ProjectRequestDto.JoinProjectCreateRequestDto request) {
        return Project.builder()
                .name(request.getPname())
                .description(request.getDescription())
                .isPrivate(request.getIsPrivate())
                .build();
    }
}