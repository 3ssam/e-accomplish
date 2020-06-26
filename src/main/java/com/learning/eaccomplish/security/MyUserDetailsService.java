package com.learning.eaccomplish.security;

import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private ParentRepository parentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Parent parent = parentRepository.findByEmail(username);
        if (parent == null)
            throw new UsernameNotFoundException("this username is not exist");
        return new MyUserDetails(parent);
    }

}
