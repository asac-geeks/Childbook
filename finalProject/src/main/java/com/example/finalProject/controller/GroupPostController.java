package com.example.finalProject.controller;

import com.example.finalProject.repository.GroupPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupPostController {

    @Autowired
    GroupPostRepository groupPostRepository;

    // @PostMapping("/group/{groupid}")
    // @GetMapping("/group/{groupid}")
}

/*
* the logged in user.
*
*
*
*
*/