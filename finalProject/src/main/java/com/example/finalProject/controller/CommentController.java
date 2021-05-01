package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Comment;
import com.example.finalProject.entity.Post;
import com.example.finalProject.entity.TemporaryComment;
import com.example.finalProject.repository.PostRepository;
import com.example.finalProject.repository.TemporaryCommentRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CommentController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TemporaryCommentRepository temporaryCommentRepository;

    @PostMapping("/addComment/{id}")
    public ResponseEntity<Comment> addComment(@PathVariable Integer id, @RequestBody TemporaryComment temporaryComment) {
        if((SecurityContextHolder.getContext().getAuthentication()) != null){
            AppUser appUser = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            Post post = postRepository.findById(id).get();
            temporaryComment.setAppUser(appUser);
            temporaryComment.setPost(post);
            temporaryComment = temporaryCommentRepository.save(temporaryComment);
            return new ResponseEntity(temporaryComment, HttpStatus.OK);
        };
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
