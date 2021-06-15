package com.example.finalProject.models;

public class IsSeenRequest {
    private String type;

    public IsSeenRequest(String type) {
        this.type = type;
    }

    public IsSeenRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
