package com.cau.issuemanagement.issuestation.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Role")
@Table(name = "role")
public class RoleEntity {
    @Id
    @Column(name = "roleid", nullable = false, columnDefinition = "NULL")
    private int roleid;

    @Column(name = "name", nullable = false, length = 20, columnDefinition = "tester")
    private String name;
}