package com.issuestation.Repository;

import com.issuestation.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findById(String id);
    Optional<UserEntity> findByNickname(String nickname);

    public boolean existsByIdAndPw(String id, String pw);
}
