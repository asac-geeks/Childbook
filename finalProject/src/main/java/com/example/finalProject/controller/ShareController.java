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

    @PostMapping("/share/{id}")
    public ResponseEntity sharePost(@PathVariable int id) {
        if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
            AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            Post post = postRepository.findById(id).get();
            temporaryShareRepository.save(new TemporaryShare(userDetails, post));
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/shareverification/{id}")
    public ResponseEntity<Share> shareVerification(@PathVariable Integer id, @RequestBody VerificationRequest verificationRequest) {
        Share share = new Share();
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                Parent parent = parentRepository.findByParentEmailAndParentPassword(verificationRequest.getParentEmail(), verificationRequest.getSerialNumber());
                System.out.println(parent);
                System.out.println(userDetails.getParent().getId());
                System.out.println(verificationRequest.getParentEmail());
                System.out.println(verificationRequest.getSerialNumber());
                System.out.println("قبل");
                if (parent != null && userDetails.getParent().getId() == parent.getId()) {
                    System.out.println("بعد");
                    System.out.println(parent);
                    TemporaryShare temporaryShare = temporaryShareRepository.findById(id).get();
                    share.setAppUser(userDetails);
                    share.setPost(temporaryShare.getPost());
                    System.out.println("sssssssssssssssssss");
//                    share.setPost(temporaryShare.getPost());
                    share = shareRepository.save(share);
                    System.out.println("سسسسسسسسسسسسسسسسس");
                    temporaryShareRepository.delete(temporaryShare);
                    System.out.println("سsssssssssssssssssssسسسسسسسسسسسسسسسس");
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
