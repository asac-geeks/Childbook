package com.example.finalProject.repository;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Parent;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;


public interface UserRepository extends CrudRepository<AppUser, Integer> {

	AppUser findByUserName(String username);
	Set<AppUser> findByParent(Parent parent);
}
