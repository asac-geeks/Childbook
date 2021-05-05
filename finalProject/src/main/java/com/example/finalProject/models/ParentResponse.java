package com.example.finalProject.models;

import com.example.finalProject.entity.Comment;
import com.example.finalProject.entity.Parent;
import com.example.finalProject.entity.Post;
import com.example.finalProject.entity.Share;

import java.util.HashSet;
import java.util.Set;

public class ParentResponse {
    private Set<Comment> comments = new HashSet();
    private Set<Post> posts = new HashSet();
    private Set<Share> shares = new HashSet();
    private Parent parent;

    public ParentResponse() {
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Share> getShares() {
        return shares;
    }

    public void setShares(Set<Share> shares) {
        this.shares = shares;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
