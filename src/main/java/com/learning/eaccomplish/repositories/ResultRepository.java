package com.learning.eaccomplish.repositories;

import com.learning.eaccomplish.models.Report;
import com.learning.eaccomplish.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllByReport(Report report);
}
