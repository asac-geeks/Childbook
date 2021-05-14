package com.example.finalProject.models;

import com.example.finalProject.entity.*;

import java.util.HashSet;
import java.util.Set;

public class ParentResponse {
    private Set<TemporaryComment> comments = new HashSet();
    private Set<TemporaryPost> posts = new HashSet();
    private Set<TemporaryShare> shares = new HashSet();
    private Set<AppUser> children = new HashSet();
    private Parent parent;

    public ParentResponse() {
    }

    public Set<TemporaryComment> getComments() {
        return comments;
    }

    public void setComments(Set<TemporaryComment> comments) {
        this.comments = comments;
    }

    public Set<TemporaryPost> getPosts() {
        return posts;
    }

    public void setPosts(Set<TemporaryPost> posts) {
        this.posts = posts;
    }

    public Set<TemporaryShare> getShares() {
        return shares;
    }

    public void setShares(Set<TemporaryShare> shares) {
        this.shares = shares;
    }

    public Set<AppUser> getChildren() {
        return children;
    }

    public void setChildren(Set<AppUser> children) {
        this.children = children;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
