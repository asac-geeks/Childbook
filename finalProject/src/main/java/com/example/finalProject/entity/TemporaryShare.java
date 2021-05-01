package com.example.finalProject.entity;

import javax.persistence.Entity;

@Entity
public class TemporaryShare extends Share {
    public TemporaryShare() {
    }

    public TemporaryShare(AppUser appUser, Post post) {
        super(appUser, post);
    }
}
