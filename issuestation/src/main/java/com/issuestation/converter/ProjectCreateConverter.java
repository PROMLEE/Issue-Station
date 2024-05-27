package com.issuestation.converter;

import com.issuestation.Dto.ProjectCreateRequestDto;
import com.issuestation.Dto.ProjectCreateResponseDto;
import com.issuestation.Entity.ProjectEntity;

import java.time.LocalDate;

public class ProjectCreateConverter {
    public static ProjectCreateResponseDto.JoinProjectCreateResponseDto toJoinProjectCreateResponseDto(ProjectEntity project) {

        return ProjectCreateResponseDto.JoinProjectCreateResponseDto.builder()
                .pid(project.getPid())
                .build();
    }

    public static ProjectEntity toProjectEntity(ProjectCreateRequestDto.JoinProjectCreateRequestDto request) {
        return ProjectEntity.builder()
                .pname(request.getPname())
                .description(request.getDescription())
                .isprivate(request.getIsprivate())
                .build();
    }
}