package com.issuestation.Repository;

import com.issuestation.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByIssueId(Long issueId);
}
