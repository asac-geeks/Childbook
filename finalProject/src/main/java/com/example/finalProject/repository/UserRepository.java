package com.example.finalProject.repository;

import com.example.finalProject.entity.AppUser;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<AppUser, Integer> {

	AppUser findByUserName(String username);

}
