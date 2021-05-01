package com.example.finalProject.models;

import javax.persistence.Column;

public class VerificationRequest {
    private String parentEmail;
    private String serialNumber;

    public VerificationRequest(String parentEmail, String serialNumber) {
        this.parentEmail = parentEmail;
        this.serialNumber = serialNumber;
    }

    public VerificationRequest() {
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
}
