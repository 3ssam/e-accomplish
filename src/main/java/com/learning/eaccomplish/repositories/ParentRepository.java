package com.learning.eaccomplish.repositories;

import com.learning.eaccomplish.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {
    boolean existsByEmail(String email);
}
