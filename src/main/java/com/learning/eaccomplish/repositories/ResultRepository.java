package com.learning.eaccomplish.repositories;

import com.learning.eaccomplish.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Long, Result> {
}
