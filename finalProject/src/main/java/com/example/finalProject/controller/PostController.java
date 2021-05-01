package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Post;
import com.example.finalProject.entity.TemporaryPost;
import com.example.finalProject.repository.ParentRepository;
import com.example.finalProject.repository.PostRepository;
import com.example.finalProject.repository.TemporaryPostRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PostController {
    @Autowired
    TemporaryPostRepository temporaryPostRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/addPost")
    public ResponseEntity<Post> addPost(@RequestBody TemporaryPost temporaryPost) {
        AppUser appUser = userRepository.findById(1).get();
        temporaryPost.setAppUser(appUser);
        appUser.getParent().getParentPassword();
        temporaryPost = temporaryPostRepository.save(temporaryPost);
        return new ResponseEntity(temporaryPost, HttpStatus.OK);
    }


}
