package com.issuestation.Entity.Temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentId implements Serializable {
    private int commentid;
    private int pid;
    private int issueid;
    private int stateid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentId commentId = (CommentId) o;
        return (commentid == commentId.commentid) && (pid == commentId.pid) && (issueid == commentId.issueid) && (stateid == commentId.stateid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentid, issueid);
    }
}
