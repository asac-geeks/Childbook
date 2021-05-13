package com.example.finalProject.repository;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Share;
import com.example.finalProject.entity.TemporaryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TemporaryCommentRepository extends JpaRepository<TemporaryComment,Integer> {
    Set<TemporaryComment> findByAppUser(AppUser appUser);

}
