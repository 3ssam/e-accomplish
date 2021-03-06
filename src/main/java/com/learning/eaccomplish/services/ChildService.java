package com.learning.eaccomplish.services;

import com.learning.eaccomplish.models.Child;
import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.models.Quiz;
import com.learning.eaccomplish.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChildService {

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;


    @Transactional
    public void createChild(Child child, Parent parent) {
        child.setParent(parent);
        child.setLastQuestion(questionRepository.getOne(201L));
        child.setLastQuiz(quizRepository.getOne(1L));
        childRepository.save(child);
//        List<Child> children = parent.getChildren();
//        children.add(child);
//        parent.setChildren(children);
//        if (children.size() == 2)
//            parent.setFullChild(true);
//        parentRepository.save(parent);
    }


    public Child getChild(String name, String pinCode, Parent parent) {
        Child child = childRepository.getByNameAndPinCodeAndParent(name, pinCode, parent);
        return child;
    }

    public Child getChild(long id) {
        Child child = childRepository.findById(id).get();
        return child;
    }


    public List<Child> getChilds(Parent parent) {
        List<Child> children = childRepository.findAllByParent(parent);
        return children;
    }

    @Transactional
    public void deleteChild(Long id) {
        Child child = childRepository.getOne(id);
        if (child == null)
            throw new IllegalArgumentException("Invalid Child Id:" + id);
        childRepository.delete(child);
    }

    @Transactional
    public void EditChild(Child child) {
        childRepository.save(child);
    }

}
