package com.example.finalProject.repository;

import com.example.finalProject.entity.Groups;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Groups, Integer> {
    List<Groups> findByTitle(String title);
}
