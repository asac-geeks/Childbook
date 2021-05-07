package com.example.finalProject.service;

import java.util.ArrayList;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.repository.UserRepository;
import com.example.finalProject.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService  implements UserDetailsService{

	@Autowired
	private UserRepository repository;

	@Autowired
	private ParentRepository parentRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails user = repository.findByUserName(username);

		if(user == null){
			user = parentRepository.findByUserName(username);
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

}
