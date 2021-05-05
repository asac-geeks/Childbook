package com.example.finalProject.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private AppUser appUser;

    @ManyToOne
    private Post post;

    // ................................. Yazan added by ......................................
    @ManyToOne
    private GroupPost groupPost;

    public Comment(AppUser appUser, Post post, String body, GroupPost groupPost) {
        this.appUser = appUser;
        this.post = post;
        this.body = body;
        this.groupPost = groupPost;
    }

    public GroupPost getGroupPost() {
        return groupPost;
    }

    public void setGroupPost(GroupPost groupPost) {
        this.groupPost = groupPost;
    }

    // ................................. Yazan added by ......................................
    private String body;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedAt")
    private Date modifiedAt;

    public Comment(AppUser appUser, Post post, String body) {
        this.appUser = appUser;
        this.post = post;
        this.body = body;
    }

    public Comment() {
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
