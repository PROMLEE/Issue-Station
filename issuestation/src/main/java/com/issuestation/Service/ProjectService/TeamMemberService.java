package com.issuestation.Service.ProjectService;

import com.issuestation.Dto.Project.ProjectResponseDto;
import com.issuestation.Entity.Team;
import com.issuestation.Repository.TeamRepository;
import com.issuestation.converter.ProjectConverter;
import com.issuestation.converter.TeamConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamRepository teamRepository;

    public ResponseEntity<List<ProjectResponseDto.TeamMemberDTO>> getTeamsByProjectId(Long id) {
        List<ProjectResponseDto.TeamMemberDTO> teams = teamRepository.findByProjectId(id).stream()
                .map(team -> new ProjectResponseDto.TeamMemberDTO(
                        team.getIsAdmin(),
                        team.getRole(),
                        team.getUser().getNickname()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }
}
