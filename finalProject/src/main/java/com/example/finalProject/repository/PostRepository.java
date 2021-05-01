package com.example.finalProject.repository;

import com.example.finalProject.entity.Likes;
import com.example.finalProject.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
