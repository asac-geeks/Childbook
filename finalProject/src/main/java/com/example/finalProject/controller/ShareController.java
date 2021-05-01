package com.example.finalProject.controller;

import com.example.finalProject.entity.*;
import com.example.finalProject.repository.PostRepository;
import com.example.finalProject.repository.TemporaryShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ShareController {

    @Autowired
    TemporaryShareRepository temporaryComment;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/share/{id}")
    public ResponseEntity<Share> sharePost(@PathVariable int id) {
        Post post = postRepository.findById(id).get();
        post.getAppUser();
        String parentPassword = post.getAppUser().getParent().getParentPassword();
        return new ResponseEntity(post, HttpStatus.OK);
    }

}
