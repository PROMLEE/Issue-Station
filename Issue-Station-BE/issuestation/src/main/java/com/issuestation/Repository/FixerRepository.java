package com.issuestation.Repository;

import com.issuestation.Entity.Fixer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FixerRepository extends JpaRepository<Fixer, Long> {
    void deleteAllByIssueId(Long id);
    Optional<Fixer> findDistinctFirstByIssueId(Long issueId);

}
