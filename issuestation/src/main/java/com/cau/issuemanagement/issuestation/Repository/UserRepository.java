package com.cau.issuemanagement.issuestation.Repository;

import com.cau.issuemanagement.issuestation.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
