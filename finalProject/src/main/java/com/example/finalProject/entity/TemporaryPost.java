package com.example.finalProject.entity;

import javax.persistence.*;

@Entity
public class TemporaryPost extends Post {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    Post sourcePost;
//    private String parentEmail;
//    @Column(nullable = true)
//    private String serialNumber;

//    public TemporaryPost() {
//    }
//
//    public TemporaryPost(Post sourcePost, String parentEmail, String serialNumber) {
//        this.sourcePost = sourcePost;
//        this.parentEmail = parentEmail;
//        this.serialNumber = serialNumber;
//    }
//
//    public Post getSourcePost() {
//        return sourcePost;
//    }
//
//    public void setSourcePost(Post sourcePost) {
//        this.sourcePost = sourcePost;
//    }
//
//    public String getParentEmail() {
//        return parentEmail;
//    }
//
//    public void setParentEmail(String parentEmail) {
//        this.parentEmail = parentEmail;
//    }
//
//    public String getSerialNumber() {
//        return serialNumber;
//    }
//
//    public void setSerialNumber(String serialNumber) {
//        this.serialNumber = serialNumber;
//    }


    public TemporaryPost() {
    }

    public TemporaryPost(String body, AppUser appUser, String imageUrl, String videoSrc, String videoType, String postTitle, boolean isPublic) {
        super(body, appUser, imageUrl, videoSrc, videoType, postTitle, isPublic);
    }
}
