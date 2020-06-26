package com.learning.eaccomplish.services;

import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.repositories.ParentRepository;
import com.learning.eaccomplish.repositories.RoleRepository;
import com.learning.eaccomplish.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public void createParent(Parent parent) {
        parent.setActivated(true);
        parent.setFullChild(false);
        parent.setPassword(passwordEncoder.encode(parent.getPassword()));
        parent.setRole(roleRepository.getByRoleName("PARENT"));
        UserRepository.save(parent);
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
        UserRepository.delete(parent);
        parentRepository.delete(parent);
    }

    @Transactional
    public void EditParent(Parent parent) {
        UserRepository.save(parent);
        parentRepository.save(parent);
    }

}
