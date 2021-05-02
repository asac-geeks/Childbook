package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Likes;
import com.example.finalProject.entity.Post;
import com.example.finalProject.repository.LikesRepositoy;
import com.example.finalProject.repository.PostRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class LikesController {
    @Autowired
    LikesRepositoy likesRepositoy;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PostRepository postRepository;

    @PostMapping("/likepost/{id}")
    public RedirectView addLike(@PathVariable Integer id){
        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                Likes like =  likesRepositoy.save(new Likes(userDetails,postRepository.findById(id).get()));
            }

        }catch (Exception ex){
            return new RedirectView("/error?message=Used%username");
        }
        return new RedirectView("/");
    }

    @DeleteMapping("/deletelike/{id}")
    public ResponseEntity handleDeleteLike(@PathVariable Integer id) {
        System.out.println(id);
        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                Likes likes = likesRepositoy.findById(id).get();
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                if (likes != null && userDetails.getId() == likes.getAppUser().getId()){
                    likesRepositoy.deleteById(id);
                }
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
