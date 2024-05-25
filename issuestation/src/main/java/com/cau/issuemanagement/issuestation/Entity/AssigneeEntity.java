package com.cau.issuemanagement.issuestation.Entity;

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
@Entity(name = "Assignee")
@Table(name = "Assignee")
public class AssigneeEntity {
    @Id
    @Column(name = "userId", nullable = false)
    private int userid;

    @Column(name = "id", nullable = false, length = 20)
    private String id;
    @Column(name = "pw", nullable = false, length = 20)
    private String pw;
    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;
}
