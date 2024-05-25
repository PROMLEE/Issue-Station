package com.cau.issuemanagement.issuestation.Repository;
import com.cau.issuemanagement.issuestation.Entity.AssigneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssigneeRepository extends JpaRepository<AssigneeEntity, Integer> {

}
