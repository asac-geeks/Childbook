package com.example.finalProject.controller;

import com.example.finalProject.entity.*;
import com.example.finalProject.models.VerificationRequest;
import com.example.finalProject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShareController {

    @Autowired
    TemporaryShareRepository temporaryShareRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    ChildEventsRepository childEventsRepository;

    @PostMapping("/share/{id}")
    public ResponseEntity sharePost(@PathVariable int id) {
        if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
            AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            Post post = postRepository.findById(id).get();
            TemporaryShare share = new TemporaryShare(userDetails, post);
            childEventsRepository.save(new ChildEvents(userDetails.getParent(),share));
            temporaryShareRepository.save(share);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/shareverification/{id}")
    public ResponseEntity<Share> shareVerification(@PathVariable Integer id) {
        Share share = new Share();
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                Parent parent = parentRepository.findByParentEmail(SecurityContextHolder.getContext().getAuthentication().getName());
                TemporaryShare temporaryShare = temporaryShareRepository.findById(id).get();
                AppUser userDetails = userRepository.findByUserName(temporaryShare.getAppUser().getUserName());
                if (parent != null && userDetails.getParent().getId() == parent.getId()) {
                    share.setAppUser(userDetails);
                    share.setPost(temporaryShare.getPost());
                    share = shareRepository.save(share);
                    temporaryShareRepository.delete(temporaryShare);
                } else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }

        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(share, HttpStatus.OK);
    }

    @DeleteMapping("/deleteshare/{id}")
    public ResponseEntity handleDeleteShare(@PathVariable Integer id) {
        System.out.println(id);
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                Share share = shareRepository.findById(id).get();
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                if (share != null && userDetails.getId() == share.getAppUser().getId()) {
                    shareRepository.deleteById(id);
                }
            }
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
