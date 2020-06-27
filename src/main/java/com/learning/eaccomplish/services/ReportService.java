package com.learning.eaccomplish.services;

import com.learning.eaccomplish.models.Child;
import com.learning.eaccomplish.models.Quiz;
import com.learning.eaccomplish.models.Report;
import com.learning.eaccomplish.repositories.ReportRepository;
import com.learning.eaccomplish.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;


    @Transactional
    public Report createReport(Child child, Quiz quiz) {
        Report report = new Report();
        report.setQuiz(quiz);
        report.setChild(child);
        report.setDate(LocalDate.now().toString());
        report.setNumOfRight(0);
        report.setNumOfRight(0);
        report.setNumOfRight(0);
        reportRepository.save(report);
        return report;
    }

//    public boolean mailIsExist(Parent parent) {
//        return parentRepository.existsByEmail(parent.getEmail());
//
//    }
//
    public Report getReport(Child child, Quiz quiz) {
        Report report = reportRepository.findByChildAndQuiz(child,quiz);
        return report;
    }

    public List<Report> getReport(Child child, String date) {
        List<Report> report = reportRepository.findByChildAndDate(child,date);
        return report;
    }

    public List<Report> getReport(Child child) {
        List<Report> report = reportRepository.findAllByChild(child);
        return report;
    }



    public Report getReport(Long id) {
        Report report = reportRepository.findById(id).get();
        return report;
    }


//    @Transactional
//    public void deleteParent(Long id) {
//        Parent parent = parentRepository.getOne(id);
//        if (parent == null)
//            throw new IllegalArgumentException("Invalid Parent Id:" + id);
//        parentRepository.delete(parent);
//    }
//
    @Transactional
    public void editReport(Report report) {
        reportRepository.save(report);
    }
//
////    @Transactional
////    public void addChild(Child child, Parent parent) {
////        List<Child> children = parent.getChildren();
////        children.add(child);
////        parent.setChildren(children);
////        if (children.size() == 2)
////            parent.setFullChild(true);
////        parentRepository.save(parent);
////    }
//
}
