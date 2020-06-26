package com.learning.eaccomplish.security;

import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.repositories.ParentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrentUser {
    private static ParentRepository parentRepository;

    private CurrentUser(ParentRepository parentRepository) {
        CurrentUser.parentRepository = parentRepository;
    }

    public static long getId() {
        return getPrincipal().map(MyUserDetails::getId).orElse(-1L);
    }

    public static Parent getUser() {
        return getPrincipal().map(MyUserDetails::getUser).orElse(null);
    }


    private static Optional<MyUserDetails> getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication).map(auth -> ((MyUserDetails) auth.getPrincipal()));
    }
}
