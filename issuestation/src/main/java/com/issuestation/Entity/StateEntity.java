package com.issuestation.Entity;

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
@Entity(name = "State")
@Table(name = "state")
public class StateEntity {
    @Id
    @Column(name = "stateid", nullable = false, columnDefinition = "NULL")
    private int stateid;

    @Column(name = "name", nullable = false, length = 10, columnDefinition = "NULL")
    private String name;
}