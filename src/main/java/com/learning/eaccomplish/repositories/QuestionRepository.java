package com.learning.eaccomplish.repositories;

import com.learning.eaccomplish.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Long, Question> {
}
