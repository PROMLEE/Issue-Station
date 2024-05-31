package com.issuestation.Entity;

import com.issuestation.Entity.Temp.IssueId;
import com.issuestation.Entity.common.BaseTimeEntity;
import com.issuestation.Entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Issue extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(nullable = false, length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


    //이거 이슈 상태가 바뀔 때, initdate가 null이 되어버려서 강제로 이렇게 해버렸는데 상관없나?
//    public void updateStatus(Status newStatus) {
//        this.status = newStatus;
//    }

}
