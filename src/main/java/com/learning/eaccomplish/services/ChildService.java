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
    public void createChild(Child child,Parent parent) {
        child.setParent(parent);
        child.setLastQuestion(questionRepository.getOne(201L));
        child.setLastQuiz(quizRepository.getOne(1L));
        childRepository.save(child);
    }


    public Child getChild(String name,String pinCode) {
        Child child = childRepository.getByNameAndPinCode(name,pinCode);
        return child;
    }


    public List<Child> getChilds(Parent parent) {
        List<Child> children = childRepository.findAllByParent(parent);
        return children;
    }

    @Transactional
    public void deleteParent(Long id) {
        Parent parent = parentRepository.getOne(id);
        if (parent == null)
            throw new IllegalArgumentException("Invalid Parent Id:" + id);
        parentRepository.delete(parent);
    }

    @Transactional
    public void EditParent(Parent parent) {
        parentRepository.save(parent);
    }

}
