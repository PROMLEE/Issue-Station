package com.issuestation.Service.ProjectService;

import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Entity.Team;
import com.issuestation.Repository.TeamRepository;
import com.issuestation.converter.TeamConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamJoinServicelmpl implements TeamJoinService{
    private final TeamRepository teamJoinRepository;
    private final TeamConverter teamConverter;

    @Override
    @Transactional
    public Team joinTeam(ProjectRequestDto.JoinTeamRequestDto request, long projectId) {
        Team newTeam = teamConverter.toTeamEntity(request, projectId);
        return teamJoinRepository.save(newTeam);
    }

}
