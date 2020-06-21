package com.learning.eaccomplish.repositories;

import com.learning.eaccomplish.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Long, Quiz> {
}
