package com.example.finalProject.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Parent {
    public Parent(String parentEmail, String parentPassword) {
        this.parentEmail = parentEmail;
        this.parentPassword = parentPassword;
    }

    public Parent() {
    }

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<AppUser> appUsers;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String parentEmail;
    private String parentPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getParentPassword() {
        return parentPassword;
    }

    public void setParentPassword(String parentPassword) {
        this.parentPassword = parentPassword;
    }

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }
}
