package com.issuestation.Entity;

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
@Entity(name = "Comment")
@Table(name = "comment")
@IdClass(CommentId.class)
public class CommentEntity {

    @Id
    @Column(name = "commentid", nullable = false)
    private int commentid;

    @Id
    @Column(name = "issueid", nullable = false)
    private int issueid;

    @Id
    @Column(name = "pid", nullable = false)
    private int pid;

    @Id
    @Column(name = "stateid", nullable = false)
    private int stateid;

    @Column(name = "userid", nullable = false)
    private int userid;

    @Column(name = "initdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date initdate;

    @Column(name = "tag", nullable =true, length=20)
    private String tag;

    @Column(name = "comment", nullable =false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "issueid", referencedColumnName = "issueid", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "commentToIssueid"))
    private IssueEntity issue;

}
