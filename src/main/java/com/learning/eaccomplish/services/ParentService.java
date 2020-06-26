package com.learning.eaccomplish.services;

import com.learning.eaccomplish.models.Child;
import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.repositories.ParentRepository;
import com.learning.eaccomplish.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public void createParent(Parent parent) {
        parent.setActivated(true);
        parent.setFullChild(false);
        List<Child> children = new ArrayList<>();
        parent.setChildren(children);
        parent.setPassword(passwordEncoder.encode(parent.getPassword()));
        parent.setRole(roleRepository.getByRoleName("PARENT"));
        parentRepository.save(parent);
    }

    public boolean mailIsExist(Parent parent) {
        return parentRepository.existsByEmail(parent.getEmail());

    }

    public Parent getParent(Long id) {
        Parent parent = parentRepository.getOne(id);
        if (parent == null)
            throw new IllegalArgumentException("Invalid Parent Id:" + id);
        return parent;
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

//    @Transactional
//    public void addChild(Child child, Parent parent) {
//        List<Child> children = parent.getChildren();
//        children.add(child);
//        parent.setChildren(children);
//        if (children.size() == 2)
//            parent.setFullChild(true);
//        parentRepository.save(parent);
//    }

}
