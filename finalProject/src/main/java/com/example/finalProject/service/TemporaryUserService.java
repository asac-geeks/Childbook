package com.example.finalProject.service;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.TemporaryUser;
import com.example.finalProject.repository.TemporaryUserRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class TemporaryUserService  implements UserDetailsService {
    @Autowired
    private TemporaryUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TemporaryUser user = repository.findByUserName(username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());

    }
    public UserDetails loadUserByParentEmail(String parentEmail) throws UsernameNotFoundException {

        TemporaryUser user = repository.findByParentEmail(parentEmail);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());

    }

}
