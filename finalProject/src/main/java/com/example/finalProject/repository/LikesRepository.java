package com.example.finalProject.repository;

import com.example.finalProject.entity.Comment;
import com.example.finalProject.entity.Likes;
import org.springframework.data.repository.CrudRepository;

public interface LikesRepository extends CrudRepository<Likes, Integer> {
}
