package com.issuestation.Entity;

import com.issuestation.Entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //이거 넣어줘야 전달받지않은 데이터인데 db에서 자동으로 증가됨 내가 db에 Auto_INCREMENT넣어놨음 속성
    private Long id;

    @NotNull
    @Column(columnDefinition = "BOOL DEFAULT FALSE")
    private boolean isAdmin;

    @NotNull
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
