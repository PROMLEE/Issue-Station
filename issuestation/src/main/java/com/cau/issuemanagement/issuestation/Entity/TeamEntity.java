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
@Entity(name = "Team")
@Table(name = "team")
@IdClass(TeamId.class)
public class TeamEntity {
    @Id
    @Column(name = "teamid", nullable = false, columnDefinition = "NULL")
    private int teamid;

    @Id
    @Column(name = "pid", nullable = false, columnDefinition = "NULL")
    private int pid;

    @Id
    @Column(name = "roleid", nullable = false, columnDefinition = "NULL")
    private int roleid;

    @Column(name = "userid", nullable = false, columnDefinition = "NULL")
    private int userid;

    @Column(name = "isadmin", nullable = false, columnDefinition = "0")
    private boolean isadmin;

    @Column(name = "role", nullable = false, length = 20, columnDefinition = "tester")
    private String role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", referencedColumnName = "pid", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "teamToPid"))
    private ProjectEntity project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleid", referencedColumnName = "roleid", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "teamToRoleid"))
    private RoleEntity roleEntity;
}
