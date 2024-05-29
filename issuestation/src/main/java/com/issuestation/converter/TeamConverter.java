package com.issuestation.converter;

import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Entity.Project;
import com.issuestation.Entity.Team;
import com.issuestation.Entity.User;
import com.issuestation.Repository.ProjectRepository;
import com.issuestation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamConverter {
    private final  ProjectRepository projectRepository;
    private final  UserRepository userRepository ;

    @Autowired
    public TeamConverter(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Team toTeamEntity(ProjectRequestDto.JoinTeamRequestDto request, long projectId) {
        User user = userRepository.findByNickname(request.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID"));

        return Team.builder()
                .project(project)
                .user(user)
                .isAdmin(request.getIsAdmin())
                .role(request.getRole())
                .build();
    }
    public static ProjectResponseDto.JoinTeamResponseDto toTeamDto(Team team) {
        return ProjectResponseDto.JoinTeamResponseDto.builder()
                .id(team.getId())
                .build();
    }



}
