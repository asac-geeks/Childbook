package com.example.finalProject.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class GroupPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageUrl;
    private String videoSrc;
    private String videoType;
    private String postTitle;

    private String body;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @ManyToOne
    private AppUser GroupPostUser;

    @OneToMany(mappedBy = "groupPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;



    @OneToMany(mappedBy = "groupPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Likes> likes;

    @OneToMany(mappedBy = "groupPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Share> shares;

    @ManyToOne
    Groups group;

    public GroupPost(String body, AppUser appUser, String imageUrl, String videoSrc, String videoType, String postTitle, boolean isPublic) {
        this.body = body;
        this.GroupPostUser = appUser;   // should this be shared ?
        this.imageUrl = imageUrl;
        this.videoSrc = videoSrc;
        this.videoType = videoType;
        this.postTitle = postTitle;
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    private boolean isPublic;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public GroupPost() {
    }

    public Integer getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public AppUser getAppUser() {
        return GroupPostUser;
    }

    public void setAppUser(AppUser appUser) {
        this.GroupPostUser = appUser;
    }

    public String getVideoSrc() {
        return videoSrc;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public Set<Likes> getLikes() {
        return likes;
    }

    public void setLikes(Set<Likes> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<Share> getShares() {
        return shares;
    }

    public void setShares(Set<Share> shares) {
        this.shares = shares;
    }


}
