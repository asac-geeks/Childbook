package com.example.finalProject.controller;

import com.example.finalProject.OpenNLP.PipeLine;
import com.example.finalProject.entity.*;
import com.example.finalProject.models.VerificationRequest;
import com.example.finalProject.repository.*;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Post post = postRepository.findById(id).get();
        if ((SecurityContextHolder.getContext().getAuthentication()) != null && (SecurityContextHolder.getContext().getAuthentication()) != null && post != null && this.checkPostContent(post.getBody()) && this.checkPostContent(post.getPostTitle())) {
            AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            TemporaryShare share = new TemporaryShare(userDetails, post);
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
                Parent parent = parentRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                TemporaryShare temporaryShare = temporaryShareRepository.findById(id).get();
                AppUser userDetails = userRepository.findByUserName(temporaryShare.getAppUser().getUserName());
                System.out.println(parent);
                System.out.println(temporaryShare);
                System.out.println(userDetails);
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
            return new ResponseEntity("Deleted",HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    public boolean checkPostContent(String text){
        StanfordCoreNLP stanfordCoreNLP = PipeLine.getPipeLine();
        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreSentence> sentences = coreDocument.sentences();
        for (CoreSentence sentence : sentences) {
            String sentiment = sentence.sentiment();
            if("Negative".equals(sentiment)){
                return false;
            }
            System.out.println(sentiment + "\t" + sentence);
        }
        return true;
    }
}
