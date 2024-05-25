package com.cau.issuemanagement.issuestation.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Assignee")
@Table(name = "assignee")
public class AssigneeEntity {
    @Id
    @Column(name = "userid", nullable = false, columnDefinition = "NULL")
    private int userid;

    @Id
    @Column(name = "issueid", nullable = false, columnDefinition = "NULL")
    private int issueid;

    @Id
    @Column(name = "pid", nullable = false, columnDefinition = "NULL")
    private int pid;

    @Id
    @Column(name = "stateid", nullable = false, columnDefinition = "NULL")
    private int stateid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "assigneeToUserid"))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issueid", referencedColumnName = "issueid", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "assigneeToIssueid"))
    private IssueEntity issue;

}
