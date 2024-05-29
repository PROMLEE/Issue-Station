package com.issuestation.converter;

import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter {

    public static ProjectResponseDto.JoinProjectCreateResponseDto toProjectDto(Project project) {
        return ProjectResponseDto.JoinProjectCreateResponseDto.builder()
                .id(project.getId())
                .build();
    }

    public static Project toProjectEntity(ProjectRequestDto.JoinProjectCreateRequestDto request) {
        return Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .isPrivate(request.getIsPrivate())
                .build();
    }
}