package com.issuestation.Service.ProjectService;

import com.issuestation.Dto.Project.ProjectRequestDto;
import com.issuestation.Entity.Team;

public interface TeamJoinService {
    Team joinTeam(ProjectRequestDto.JoinTeamRequestDto request, long projectId);
}
