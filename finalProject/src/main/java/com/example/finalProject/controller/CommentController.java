package com.example.finalProject.controller;

import com.example.finalProject.entity.*;
import com.example.finalProject.models.VerificationRequest;
import com.example.finalProject.repository.*;
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
    @Autowired
    ParentRepository parentRepository;
    @Autowired
    CommentRepository commentRepository;

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

    @PostMapping("/commentverification/{id}")
    public ResponseEntity<Comment> commentVerification(@PathVariable Integer id , @RequestBody VerificationRequest verificationRequest){
        Comment comment = new Comment();
        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                Parent parent = parentRepository.findByParentEmailAndParentPassword(verificationRequest.getParentEmail(),verificationRequest.getSerialNumber());
                if (parent != null && userDetails.getParent().getId() == parent.getId()){
                    System.out.println(parent);
                    TemporaryComment temporaryPost = temporaryCommentRepository.findById(id).get();
                    comment = commentRepository.save(temporaryPost);
                    temporaryCommentRepository.delete(temporaryPost);
                }else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }

        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity(comment, HttpStatus.OK);
    };
}
