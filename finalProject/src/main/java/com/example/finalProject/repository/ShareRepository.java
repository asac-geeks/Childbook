package com.example.finalProject.repository;

import com.example.finalProject.entity.Post;
import com.example.finalProject.entity.Share;
import org.springframework.data.repository.CrudRepository;

public interface ShareRepository extends CrudRepository<Share, Integer> {
}
