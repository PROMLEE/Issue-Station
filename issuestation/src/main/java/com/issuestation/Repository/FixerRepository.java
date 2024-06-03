package com.issuestation.Repository;

import com.issuestation.Entity.Fixer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixerRepository extends JpaRepository<Fixer, Long> {
    void deleteAllByIssueId(Long id);
}
