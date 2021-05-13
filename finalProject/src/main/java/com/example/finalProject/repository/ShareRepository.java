package com.example.finalProject.repository;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Post;
import com.example.finalProject.entity.Share;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ShareRepository extends CrudRepository<Share, Integer> {
    Set<Share> findByAppUser(AppUser appUser);
}
