package com.learning.eaccomplish.repositories;

import com.learning.eaccomplish.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Long, Report> {
}
