package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Post;
import com.example.finalProject.entity.UsersFollowers;
import com.example.finalProject.repository.UserRepository;
import com.example.finalProject.repository.UsersFollowersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Set;

@Controller
public class UsersFollowersController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UsersFollowersRepository usersFollowersRepository;

    @PostMapping("/follow/{id}")
    public ResponseEntity followUser(@PathVariable int id) {
        if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
            AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            AppUser followedUser = userRepository.findById(id).get();
            UsersFollowers usersFollowers = new UsersFollowers(userDetails, followedUser);
            usersFollowersRepository.save(usersFollowers);
            return new ResponseEntity(usersFollowersRepository.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/feeds")
    public ResponseEntity renderFeeds() {
        if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
            AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            System.out.println(userDetails);
            ArrayList<Post> allFollowerPosts = new ArrayList();
            Set<UsersFollowers> allFollower = userDetails.getFollowers();
            for (UsersFollowers user : allFollower) {
                allFollowerPosts.addAll(user.getAppUserFollower().getPosts());
            }
            String nameOfUser = userDetails.getUserName();
            return new ResponseEntity(allFollower, HttpStatus.OK);

        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // ===========================salah
    @DeleteMapping("/unfollow/{id}")
    public ResponseEntity unFollowUser(@PathVariable int id) {
        if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
            AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            AppUser unFollowedUser = userRepository.findById(id).get();
            usersFollowersRepository.deleteById(unFollowedUser.getId());
            return new ResponseEntity(usersFollowersRepository.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    // ===========================salah

}


