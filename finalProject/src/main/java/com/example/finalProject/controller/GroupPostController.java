package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.GroupPost;
import com.example.finalProject.entity.Groups;
import com.example.finalProject.repository.GroupPostRepository;
import com.example.finalProject.repository.GroupRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;

import java.security.acl.Group;
import java.util.Set;

@RestController
public class GroupPostController {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupPostRepository groupPostRepository;

     @PostMapping("/group/{groupid}")
    public ResponseEntity addPostToGroup(@RequestBody GroupPost groupPost, @PathVariable Integer id){

        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser user = userRepository.findByUserName((SecurityContextHolder.getContext().getAuthentication()).getName());

                if(user.getGroups().contains(id)){
                    GroupPost post = new GroupPost(groupPost.getBody(),user,groupPost.getImageUrl(),groupPost.getVideoSrc(),groupPost.getVideoType(),groupPost.getPostTitle(),groupPost.isPublic());
                    groupPostRepository.save(post);
                }
            }
            return new ResponseEntity("/group/{groupid}", HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
     }

     @GetMapping("/group/{groupid}")
    public ResponseEntity<GroupPost> getAllGroupPosts(@PathVariable Integer id){
         Set<GroupPost> posts = null;
          try{
              if((SecurityContextHolder.getContext().getAuthentication()) != null){
                  AppUser user = userRepository.findByUserName((SecurityContextHolder.getContext().getAuthentication()).getName());
                  Groups group = groupRepository.findById(id).get();

                  if(user.getGroups().contains(id))
                     posts =  group.getGroupPosts();
              }

              return new ResponseEntity(posts, HttpStatus.OK);
          }catch (Exception ex){
              return new ResponseEntity( HttpStatus.BAD_REQUEST);
          }
     }
}
