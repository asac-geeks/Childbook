package com.example.finalProject.repository;

import com.example.finalProject.entity.Parent;
import com.example.finalProject.entity.UserStorage;
import org.springframework.data.repository.CrudRepository;

public interface UserStorageRepository   extends CrudRepository<UserStorage, Integer> {
}
