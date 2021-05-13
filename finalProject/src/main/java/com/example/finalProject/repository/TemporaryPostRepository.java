package com.example.finalProject.repository;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.TemporaryComment;
import com.example.finalProject.entity.TemporaryPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TemporaryPostRepository extends JpaRepository<TemporaryPost,Integer> {
    Set<TemporaryPost> findByAppUser(AppUser appUser);
}
