package com.issuestation.Entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reporter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //이거 넣어줘야 전달받지않은 데이터인데 db에서 자동으로 증가됨 내가 db에 Auto_INCREMENT넣어놨음 속성
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;
}
