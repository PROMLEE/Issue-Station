package com.cau.issuemanagement.issuestation.Entity;

import com.cau.issuemanagement.issuestation.Dto.SignupDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "User")
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", nullable = false)
    private int userid;

    @Column(name = "id", nullable = false, length = 20)
    private String id;
    @Column(name = "pw", nullable = false, length = 20)
    private String pw;
    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    public UserEntity(SignupDto dto) {
        //this.userid = dto.getUserid();
        this.id = dto.getId();
        this.pw = dto.getPw();
        this.nickname = dto.getNickname();
    }
}