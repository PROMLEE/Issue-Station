package com.issuestation.Service.ProjectService;

import com.issuestation.Entity.Team;
import com.issuestation.Entity.enums.Role;
import com.issuestation.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyRoleService {
    @Autowired
    private TeamRepository teamRepository;

    public Role findUserRoleInProject(Long projectId, Long userId) {
        Optional<Team> team;
        try {
            team = teamRepository.findDistinctFirstByProjectIdAndUserId(projectId, userId);
        } catch (Exception e) {
            throw new RuntimeException("User not found in project");
        }
        return team.map(Team::getRole).orElse(null);
    }
}
