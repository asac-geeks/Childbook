package com.example.finalProject.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class UserStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "userStorage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<AppUser> appUsers;

    public UserStorage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }
}
