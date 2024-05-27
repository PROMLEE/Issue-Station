package com.issuestation.Repository;

import com.issuestation.Entity.ReporterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporterRepository extends JpaRepository<ReporterEntity, Integer> {
}
