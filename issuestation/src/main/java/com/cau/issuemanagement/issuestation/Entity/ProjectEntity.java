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
@Entity(name = "Project")
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //이거 넣어줘야 전달받지않은 데이터인데 db에서 자동으로 증가됨 내가 db에 Auto_INCREMENT넣어놨음 속성
    @Column(name = "pid", nullable = false)
    private int pid;

    @Column(name = "pname", nullable = false, length = 20)
    private String pname;

    @Column(name = "isprivate", nullable = false)
    private boolean isprivate;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnaillink", length = 255, nullable = true)
    private String thumbnaillink;

    @Column(name = "initdate", nullable = false)
    private Date initdate;
}