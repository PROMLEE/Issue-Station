package com.cau.issuemanagement.issuestation.Repository;

import com.cau.issuemanagement.issuestation.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {

}
