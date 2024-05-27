package com.issuestation.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueId implements Serializable {
    private int issueid;
    private int pid;
    private int stateid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueId issueId = (IssueId) o;
        return issueid == issueId.issueid && pid == issueId.pid && stateid == issueId.stateid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueid, pid, stateid);
    }
}
