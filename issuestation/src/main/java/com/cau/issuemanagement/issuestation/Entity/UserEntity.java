package com.cau.issuemanagement.issuestation.Entity;

import com.cau.issuemanagement.issuestation.Dto.SignupDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "userid", nullable = false, columnDefinition = "NULL")
    private int userid;

    @Column(name = "id", nullable = false, length = 20, columnDefinition = "NULL")
    private String id;
    @Column(name = "pw", nullable = false, length = 20, columnDefinition = "NULL")
    private String pw;
    @Column(name = "nickname", nullable = false, length = 20, columnDefinition = "NULL")
    private String nickname;

    public UserEntity(SignupDto dto) {
        this.id = dto.getId();
        this.pw = dto.getPassword();
        this.nickname = dto.getNickname();
    }
}