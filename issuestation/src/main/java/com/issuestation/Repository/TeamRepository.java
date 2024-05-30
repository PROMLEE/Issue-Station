package com.issuestation.Repository;

import com.issuestation.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    List<Team> findByUserId(Long userId);

    List<Team> findByProjectId(Long projectId);

    Optional<Team> findDistinctFirstByProjectIdAndUserId(Long projectId, Long userId);

}
