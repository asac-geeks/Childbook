package com.example.finalProject.entity;

import javax.persistence.*;

@Entity
public class UsersFollowers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    AppUser appUser;


    @ManyToOne
    @JoinColumn(name = "follower_id")
    AppUser appUserFollower;

    public UsersFollowers(AppUser applicationUser, AppUser applicationUserFollower) {
        this.appUser = applicationUser;
        this.appUserFollower = applicationUserFollower;
    }

    public UsersFollowers() {
    }

    public Integer getId() {
        return id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public AppUser getAppUserFollower() {
        return appUserFollower;
    }
}

