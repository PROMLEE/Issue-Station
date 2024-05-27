package com.issuestation.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssigneeId implements Serializable {
    private int userid;
    private int issueid;
    private int pid;
    private int stateid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssigneeId assigneeId = (AssigneeId) o;
        return (userid == assigneeId.userid) && (issueid == assigneeId.issueid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, issueid);
    }
}
