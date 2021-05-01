package com.example.finalProject.controller;

import com.example.finalProject.entity.*;
import com.example.finalProject.models.VerificationRequest;
import com.example.finalProject.repository.ParentRepository;
import com.example.finalProject.repository.PostRepository;
import com.example.finalProject.repository.TemporaryPostRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        if((SecurityContextHolder.getContext().getAuthentication()) != null){
            AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            temporaryPost.setAppUser(userDetails);
            userDetails.getParent().getParentPassword();
            temporaryPost = temporaryPostRepository.save(temporaryPost);
            return new ResponseEntity(temporaryPost, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/postverification/{id}")
    public ResponseEntity<Post> postVerification(@PathVariable Integer id , @RequestBody VerificationRequest verificationRequest){
        Post post = new Post();
        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                Parent parent = parentRepository.findByParentEmailAndParentPassword(verificationRequest.getParentEmail(),verificationRequest.getSerialNumber());
                if (parent != null && userDetails.getParent().getId() == parent.getId()){
                    System.out.println(parent);
                    TemporaryPost temporaryPost = temporaryPostRepository.findById(id).get();
                    post = postRepository.save(temporaryPost);
                    temporaryPostRepository.delete(temporaryPost);
                }else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }

        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity(post, HttpStatus.OK);
    };

    @DeleteMapping("/post/{id}")
    public ResponseEntity handleDeletePost(@RequestParam(value = "id") Integer id) {
        System.out.println(id);
        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                Post post = postRepository.findById(id).get();
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                if (post != null && userDetails.getId() == post.getAppUser().getId()){
                    postRepository.deleteById(id);
                }
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/post/{id}")
    public ResponseEntity handleUpdatePost(@RequestParam(value = "id") Integer id,@RequestParam Post updatePost) {
        Post post = postRepository.findById(id).get();
        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                if (post != null && userDetails.getId() == post.getAppUser().getId()){
                    post.setBody(updatePost.getBody());
                    post.setPostTitle(updatePost.getPostTitle());
                    post.setImageUrl(updatePost.getImageUrl());
                    post.setPublic(post.isPublic());
                    post.setVideoSrc(post.getVideoSrc());
                    post.setVideoType(post.getVideoType());
                    postRepository.save(post);
                }
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> handleGetPost(@RequestParam(value = "id") Integer id) {
        try{
            Post post = postRepository.findById(id).get();
            return new ResponseEntity(post,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/userposts/{id}")
    public ResponseEntity<Post> handleUserPost(@RequestParam(value = "id") Integer id) {
        try{
            List<Post> posts = userRepository.findById(id).get().getPosts();
            return new ResponseEntity(posts,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/myposts")
    public ResponseEntity<List<Post>> handleMyPost(@RequestParam(value = "id") Integer id) {
        Post post = postRepository.findById(id).get();
        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                return new ResponseEntity(userDetails.getPosts(),HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
