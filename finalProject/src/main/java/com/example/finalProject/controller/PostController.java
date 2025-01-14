package com.example.finalProject.controller;

import com.example.finalProject.OpenNLP.PipeLine;
import com.example.finalProject.entity.*;
import com.example.finalProject.models.ChildTemResponse;
import com.example.finalProject.models.MyPostsResponse;
import com.example.finalProject.models.ParentResponse;
import com.example.finalProject.models.VerificationRequest;
import com.example.finalProject.repository.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

@RestController
@CrossOrigin(origins= "*")
public class PostController {
    @Autowired
    TemporaryPostRepository temporaryPostRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ParentRepository parentRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    private TemporaryCommentRepository temporaryCommentRepository;

    @Autowired
    private TemporaryShareRepository temporaryShareRepository;


    //    @Scheduled(fixedDelayString = "PT24H")
    @CrossOrigin
    @PostMapping("/addPost")
    public ResponseEntity addPost(@RequestBody TemporaryPost temporaryPost) {
        if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
            if(this.checkPostContent(temporaryPost.getBody()) && this.checkPostContent(temporaryPost.getPostTitle())){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                temporaryPost.setAppUser(userDetails);
                System.out.println("Content");
                temporaryPost = temporaryPostRepository.save(temporaryPost);
                return new ResponseEntity(temporaryPost, HttpStatus.OK);
            }else{
                System.out.println("Bad Content");

                return new ResponseEntity("Bad Content", HttpStatus.OK);
            }

        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public boolean checkPostContent(String text) {
        System.out.println(text);
        StanfordCoreNLP stanfordCoreNLP = PipeLine.getPipeLine();
        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreSentence> sentences = coreDocument.sentences();
        for (CoreSentence sentence : sentences) {
            String sentiment = sentence.sentiment();
            if ("Negative".equals(sentiment)) {
                return false;
            }
            System.out.println(sentiment + "\t" + sentence);
        }
        return true;
    }

    @PostMapping("/postverification/{id}")
    public ResponseEntity<Post> postVerification(@PathVariable Integer id) {
        Post post = new Post();
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                Parent parent = parentRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                TemporaryPost temporaryPost = temporaryPostRepository.findById(id).get();
                AppUser userDetails = userRepository.findById(temporaryPost.getAppUser().getId()).get();
                System.out.println(userDetails);
                System.out.println(parent);
                System.out.println(parent.getId());

                if (parent != null && temporaryPost.getAppUser().getParent().getId() == parent.getId()) {
                    post.setVideoType(temporaryPost.getVideoType());
                    post.setVideoSrc(temporaryPost.getVideoSrc());
                    post.setPostTitle(temporaryPost.getPostTitle());
                    post.setBody(temporaryPost.getBody());
                    post.setPublic(temporaryPost.isPublic());
                    post.setImageUrl(temporaryPost.getImageUrl());
                    post.setAppUser(userDetails);
                    System.out.println(userDetails);
                    System.out.println("salah");
                    postRepository.save(post);
                    System.out.println("salahبعد السيف");
                    temporaryPostRepository.delete(temporaryPost);
                    System.out.println("salahديليت");
                } else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }

        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(post, HttpStatus.OK);
    };

    @DeleteMapping("/post/{id}")
    public ResponseEntity handleDeletePost(@RequestParam(value = "id") Integer id) {
        System.out.println(id);
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                Post post = postRepository.findById(id).get();
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                if (post != null && userDetails.getId() == post.getAppUser().getId()) {
                    postRepository.deleteById(id);
                }
            }
            return new ResponseEntity("Deleted", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/post/{id}")
    public ResponseEntity handleUpdatePost(@PathVariable Integer id, @RequestBody Post updatePost) {
        System.out.println(updatePost);
        Post post = postRepository.findById(id).get();
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                if (post != null && userDetails.getId() == post.getAppUser().getId()) {
                    post.setBody(updatePost.getBody());
                    post.setPostTitle(updatePost.getPostTitle());
                    post.setImageUrl(updatePost.getImageUrl());
                    post.setPublic(post.isPublic());
                    post.setVideoSrc(post.getVideoSrc());
                    post.setVideoType(post.getVideoType());
                    post = postRepository.save(post);
                }
            }
            return new ResponseEntity(post, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/post/public/{id}")
    public ResponseEntity<Post> handleGetPost(@PathVariable Integer id) {
        try {
            Post post = postRepository.findById(id).get();
            if (post.isPublic()) {
                return new ResponseEntity(post, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/postTemp/public/{id}")
    public ResponseEntity handleGetPostTemp(@PathVariable Integer id) {
        try {
            TemporaryPost post = temporaryPostRepository.findById(id).get();
            return new ResponseEntity(post, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/post/My/{id}")
    public ResponseEntity<Post> handleMySinglePost(@PathVariable Integer id) {
        Post post = postRepository.findById(id).get();
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                if ((SecurityContextHolder.getContext().getAuthentication()).getName() == post.getAppUser().getUserName() || post.isPublic())
                    ;
                return new ResponseEntity(post, HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/userposts/{id}")
    public ResponseEntity<Post> handleUserPost(@PathVariable Integer id) {
        try {
            Set<Post> posts = postRepository.findByAppUser(userRepository.findById(id).get());
            return new ResponseEntity(posts, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/myposts")
    public ResponseEntity<List<Post>> handleMyPost() {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                Set<Post> posts = postRepository.findByAppUser(userDetails);
                Set<Share> shares = shareRepository.findByAppUser(userDetails);
                MyPostsResponse myPostsResponse = new MyPostsResponse(posts,shares);
                return new ResponseEntity(myPostsResponse, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/childtemp")
    public ResponseEntity childTemp() {
        try {
            System.out.println((SecurityContextHolder.getContext().getAuthentication()) ==null);
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser appUser = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                ChildTemResponse childTemResponse = new ChildTemResponse(temporaryCommentRepository.findByAppUser(appUser), temporaryPostRepository.findByAppUser(appUser), temporaryShareRepository.findByAppUser(appUser));
                return new ResponseEntity(childTemResponse, HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity( HttpStatus.OK);
    };
}
