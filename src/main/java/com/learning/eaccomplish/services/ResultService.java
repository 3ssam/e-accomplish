package com.learning.eaccomplish.services;

import com.learning.eaccomplish.models.*;
import com.learning.eaccomplish.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Transactional
    public Result createResult(Question question, Report report, String state) {
        Result result = new Result();
        result.setReport(report);
        result.setQuestion(question);
        result.setState(state);
        resultRepository.save(result);
        return result;
    }

    //
//    public boolean mailIsExist(Parent parent) {
//        return parentRepository.existsByEmail(parent.getEmail());
//
//    }
//
    public Question getNextQuestion(Question question) {
        question = questionRepository.findById(question.getId() + 1).get();
        return question;
    }

    public Quiz getNextQuiz(Quiz quiz) {
        quiz = quizRepository.findById(quiz.getId() + 1).get();
        return quiz;
    }

    public  List<Result> getResult(Report report){
        List<Result> results = resultRepository.findAllByReport(report);
        return  results;
    }

//    @Transactional
//    public void deleteParent(Long id) {
//        Parent parent = parentRepository.getOne(id);
//        if (parent == null)
//            throw new IllegalArgumentException("Invalid Parent Id:" + id);
//        parentRepository.delete(parent);
//    }
//
//    @Transactional
//    public void EditParent(Parent parent) {
//        parentRepository.save(parent);
//    }
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
