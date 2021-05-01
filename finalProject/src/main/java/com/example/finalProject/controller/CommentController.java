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
import org.springframework.stereotype.Controller;
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

    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestBody TemporaryComment temporaryComment) {
        AppUser appUser = userRepository.findById(1).get();
        Post post = postRepository.findById(1).get();
        temporaryComment.setAppUser(appUser);
        temporaryComment.setPost(post);
        String parentPassword = appUser.getParent().getParentPassword();
        temporaryComment = temporaryCommentRepository.save(temporaryComment);
        return new ResponseEntity(temporaryComment, HttpStatus.OK);
    }

}
