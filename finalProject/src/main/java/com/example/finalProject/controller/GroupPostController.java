package com.example.finalProject.controller;

import com.example.finalProject.repository.GroupPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;

@RestController
public class GroupPostController {

    @Autowired
    GroupPostRepository groupPostRepository;

     @PostMapping("/group/{groupid}")
    public RedirectView addPostToGroup(){

         return new RedirectView();
     }
    // @GetMapping("/group/{groupid}")
}

/*
* the logged in user.
*
*
*
*
*/