package com.issuestation.Repository;
import com.issuestation.Entity.AssigneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssigneeRepository extends JpaRepository<AssigneeEntity, Integer> {

}
