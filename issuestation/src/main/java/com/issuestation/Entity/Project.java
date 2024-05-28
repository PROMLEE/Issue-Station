package com.issuestation.Entity;

import com.issuestation.Entity.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //이거 넣어줘야 전달받지않은 데이터인데 db에서 자동으로 증가됨 내가 db에 Auto_INCREMENT넣어놨음 속성
    private int id;

    @Column(length = 20)
    private String name;

    @NotNull
    private Boolean isPrivate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnaillink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}