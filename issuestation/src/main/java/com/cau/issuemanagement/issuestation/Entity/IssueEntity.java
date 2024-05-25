package com.cau.issuemanagement.issuestation.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Issue")
@Table(name = "issue")
@IdClass(IssueId.class)
public class IssueEntity {
    @Id
    @Column(name = "issueid", nullable = false, columnDefinition = "NULL")
    private int issueid;

    @Id
    @Column(name = "pid", nullable = false, columnDefinition = "NULL")
    private int pid;

    @Id
    @Column(name = "stateid", nullable = false, columnDefinition = "NULL")
    private int stateid;

    @Column(name = "issuename", length = 20, nullable = false, columnDefinition = "NULL")
    private String issuename;

    @Column(name = "description", length = 100, nullable = true, columnDefinition = "NULL")
    private String description;

    @Column(name = "initdate", nullable = false, columnDefinition = "NULL")
    @Temporal(TemporalType.DATE)
    private Date initdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", referencedColumnName = "pid", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "issueToPid"))
    private ProjectEntity project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stateid", referencedColumnName = "stateid", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "issueToStateid"))
    private StateEntity state;
}
