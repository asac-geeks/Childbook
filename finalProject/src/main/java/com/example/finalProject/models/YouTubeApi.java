package com.example.finalProject.models;

import java.util.List;

public class YouTubeApi {
  String kind;
  String etag;
  List<YouTubeItems> items;

    public YouTubeApi(){}
    public YouTubeApi(String kind, String etag) {
        this.kind = kind;
        this.etag = etag;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    @Override
    public String toString() {
        return "YouTubeApi{" +
                "kind='" + kind + '\'' +
                ", etag='" + etag + '\'' +
                '}';
    }
}
