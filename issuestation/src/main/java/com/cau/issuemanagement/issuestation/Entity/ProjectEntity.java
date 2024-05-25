package com.cau.issuemanagement.issuestation.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Project")
@Table(name = "Project")
public class ProjectEntity {
    @Id
    @Column(name = "pid", nullable = false, columnDefinition = "NULL")
    private int pid;

    @Column(name = "pname", nullable = false, length = 20, columnDefinition = "NULL")
    private String pname;

    @Column(name = "isprivate", nullable = false, columnDefinition = "0")
    private boolean isprivate;

    @Column(name = "description", columnDefinition = "NULL")
    private String description;

    @Column(name = "thumbnaillink", length = 255, nullable = true, columnDefinition = "thumbnail")
    private String thumbnaillink;

    @Column(name = "initdate", nullable = false, columnDefinition = "NULL")
    private Date initdate;
}