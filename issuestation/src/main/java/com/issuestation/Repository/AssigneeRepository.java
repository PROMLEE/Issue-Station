package com.issuestation.Repository;

import com.issuestation.Entity.Assignee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssigneeRepository extends JpaRepository<Assignee, Integer> {
    Assignee findDistinctFirstByIssueId(Long id);
}
