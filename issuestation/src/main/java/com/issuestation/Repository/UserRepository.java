package com.issuestation.Repository;

import com.issuestation.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByNickname(String nickname);

    boolean existsByLoginIdAndLoginPw(String id, String pw);
}
