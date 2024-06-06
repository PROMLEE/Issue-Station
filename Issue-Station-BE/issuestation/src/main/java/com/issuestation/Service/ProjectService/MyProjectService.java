package com.issuestation.Service.ProjectService;

import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Entity.Team;
import com.issuestation.Repository.TeamRepository;
import com.issuestation.Security.TokenProvider;
import com.issuestation.converter.ProjectConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyProjectService {
    @Autowired
    private TeamRepository teamRepository;


    @Autowired
    private TokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public List<ProjectResponseDto.MyProjectResponseDto> getProjectsByUserToken(String token) {
        Long userId = Long.parseLong(jwtTokenProvider.validateJwt(token));
        List<Team> teams = teamRepository.findByUserId(userId);
        return teams.stream()
                .map(team -> ProjectConverter.toMyProjectDto(team.getProject()))
                .distinct()
                .collect(Collectors.toList());
    }
}
