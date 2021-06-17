package com.example.finalProject.models;

import java.util.ArrayList;

public class feedRes {
    ArrayList posts;

    public feedRes(ArrayList posts) {
        this.posts = posts;
    }

    public ArrayList getPosts() {
        return posts;
    }

    public void setPosts(ArrayList posts) {
        this.posts = posts;
    }
}
