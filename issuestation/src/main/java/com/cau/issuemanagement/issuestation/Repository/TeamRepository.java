package com.cau.issuemanagement.issuestation.Repository;

import com.cau.issuemanagement.issuestation.Entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity,Integer> {

}
