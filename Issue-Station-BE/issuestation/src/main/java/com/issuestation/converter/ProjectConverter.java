package com.issuestation.converter;

import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Entity.Project;
import com.issuestation.Entity.Team;
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

    public static ProjectResponseDto.MyProjectResponseDto toMyProjectDto(Project project) {
        return new ProjectResponseDto.MyProjectResponseDto(
                project.getId(),
                project.getName(),
                project.getIsPrivate(),
                project.getDescription(),
                project.getThumbnaillink(),
                project.getInitdate(),
                project.getModdate()
        );
    }

    public static ProjectResponseDto.TeamMemberDTO toTeamMemberDto(Team team) {
        return new ProjectResponseDto.TeamMemberDTO(
                team.getIsAdmin(),
                team.getRole(),
                team.getUser().getNickname()
        );
    }
}