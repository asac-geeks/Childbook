package com.example.finalProject.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class TemporaryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true)
    private String userName;
    private String password;
    private String parentEmail;

    public TemporaryUser(String userName, String password, String parentEmail, LocalDate dateOfBirth) {
        this.userName = userName;
        this.password = password;
        this.parentEmail = parentEmail;
        this.dateOfBirth = dateOfBirth;
    }

    private LocalDate dateOfBirth;

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(nullable = true)
    private String serialNumber;


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
