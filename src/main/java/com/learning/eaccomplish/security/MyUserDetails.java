package com.learning.eaccomplish.security;

import com.learning.eaccomplish.models.Parent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetails implements UserDetails {

    private Parent user;
    private long id;
    private String name;
    private String email;
    private String password;
    private boolean activated;

    public MyUserDetails(Parent user) {
        this.user = user;
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.activated = user.getActivated();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activated;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activated;
    }

    public Parent getUser() {
        return user;
    }

    public long getId() {
        return id;
    }


}
