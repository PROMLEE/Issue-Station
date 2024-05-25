package com.cau.issuemanagement.issuestation.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @Column(name = "teamid", nullable = false)
    private int teamid;

    @Id
    @Column(name = "pid", nullable = false)
    private int pid;

    @Id
    @Column(name = "roleid", nullable = false)
    private int roleid;

    @Column(name = "userid", nullable = false)
    private int userid;

    @Column(name = "isadmin", nullable = false)
    private boolean isadmin;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", referencedColumnName = "pid", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_team_project"))
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleid", referencedColumnName = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_team_role"))
    private RoleEntity roleEntity;
}
