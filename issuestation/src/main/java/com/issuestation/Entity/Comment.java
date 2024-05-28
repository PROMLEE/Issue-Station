package com.issuestation.Entity;

import com.issuestation.Entity.common.BaseTimeEntity;
import com.issuestation.Entity.enums.CommentTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private CommentTag tag;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;
}
