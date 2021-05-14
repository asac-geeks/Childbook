package com.example.finalProject.repository;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Comment;
import com.example.finalProject.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Set<Comment> findByAppUser(AppUser appUser);

}
