package com.issuestation.Repository;

import com.issuestation.Entity.Reporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReporterRepository extends JpaRepository<Reporter, Integer> {
    Optional<Reporter> findDistinctFirstByIssueId(Long id);
}
