package com.issuestation.Entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String loginId;

    @Column(nullable = false, length = 20)
    private String loginPw;

    @Column(nullable = false, length = 20)
    private String nickname;

//    public User(SignupDto dto) {
//        //this.userid = dto.getUserid();
//        this.loginId = dto.getLoginId();
//        this.loginPw = dto.getLoginPw();
//        this.nickname = dto.getNickname();
//    }

}