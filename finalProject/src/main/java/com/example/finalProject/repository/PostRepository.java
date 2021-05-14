package com.example.finalProject.repository;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Likes;
import com.example.finalProject.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends CrudRepository<Post, Integer> {
    Set<Post> findByAppUser(AppUser appUser);

}
