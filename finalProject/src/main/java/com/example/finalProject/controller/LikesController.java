package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Likes;
import com.example.finalProject.repository.LikesRepositoy;
import com.example.finalProject.repository.PostRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
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
    };
}
