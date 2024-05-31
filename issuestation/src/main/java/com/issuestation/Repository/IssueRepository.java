package com.issuestation.Repository;

import com.issuestation.Entity.Issue;
import com.issuestation.Entity.enums.Status;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByProjectId(Long projectId);

    List<Issue> findByProjectIdAndNameContainingAndStatus(Long project_id, String name, @NotNull Status status);

    List<Issue> findByProjectIdAndNameContaining(Long projectId, String name);
}

