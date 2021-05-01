package com.example.finalProject.entity;

import javax.persistence.Entity;

@Entity
public class TemporaryComment extends Comment {
    public TemporaryComment() {
    }

    public TemporaryComment(AppUser appUser, Post post, String body) {
        super(appUser, post, body);
    }
}
