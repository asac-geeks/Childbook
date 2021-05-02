package com.example.finalProject.entity;

import javax.persistence.*;

@Entity
public class TemporaryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true)
    private String userName;
    private String password;
    private String parentEmail;
    @Column(nullable = true)
    private String serialNumber;


    public TemporaryUser(String username, String password, String parentEmail) {
        this.userName = username;
        this.password = password;
        this.parentEmail = parentEmail;
    }

    public TemporaryUser() {

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "TemporaryUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", parentEmail='" + parentEmail + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
