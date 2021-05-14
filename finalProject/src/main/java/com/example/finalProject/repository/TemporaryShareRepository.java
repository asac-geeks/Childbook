package com.example.finalProject.repository;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.TemporaryPost;
import com.example.finalProject.entity.TemporaryShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TemporaryShareRepository extends JpaRepository<TemporaryShare,Integer> {
    Set<TemporaryShare> findByAppUser(AppUser appUser);
}
