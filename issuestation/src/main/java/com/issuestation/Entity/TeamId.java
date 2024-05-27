package com.issuestation.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamId implements Serializable {
    private int teamid;
    private int pid;
    private int roleid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamId teamId = (TeamId) o;
        return teamid == teamId.teamid && pid == teamId.pid && roleid == teamId.roleid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamid, pid, roleid);
    }
}
