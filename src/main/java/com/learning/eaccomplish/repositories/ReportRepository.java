package com.learning.eaccomplish.repositories;

import com.learning.eaccomplish.models.Child;
import com.learning.eaccomplish.models.Quiz;
import com.learning.eaccomplish.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Report findByChildAndQuiz(Child child, Quiz quiz);

    List<Report> findByChildAndDate(Child child, String date);
}
