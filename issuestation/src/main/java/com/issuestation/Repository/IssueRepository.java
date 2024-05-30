package com.issuestation.Repository;

import com.issuestation.Entity.Issue;
import com.issuestation.Entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByProjectId(Long projectId);
    List<Issue> findByProjectIdAndNameAndStatus(Long projectId, String name, Status status);
}

