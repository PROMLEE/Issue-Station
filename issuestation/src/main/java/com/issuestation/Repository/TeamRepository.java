package com.issuestation.Repository;

import com.issuestation.Entity.Team;
import com.issuestation.Entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    List<Team> findByUserId(Long userId);

    List<Team> findByProjectId(Long projectId);

    Optional<Team> findDistinctFirstByProjectIdAndUserId(Long projectId, Long userId);
    @Query("SELECT t FROM Team t WHERE t.project.id = :projectId AND t.role = :role")
    List<Team> findByProjectIdAndRole(@Param("projectId") Long projectId, @Param("role") Role role);
}
