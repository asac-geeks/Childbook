package com.example.finalProject.models.youtube;

import java.util.List;

public class YouTubeApi {
  String kind;
  String etag;
  String nextPageToken; // we might need it in future
  List<YouTubeItems> items;

    public YouTubeApi(){}

    public YouTubeApi(String kind, String etag, String nextPageToken, List<YouTubeItems> items) {
        this.kind = kind;
        this.etag = etag;
        this.nextPageToken = nextPageToken;
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<YouTubeItems> getItems() {
        return items;
    }

    public void setItems(List<YouTubeItems> items) {
        this.items = items;
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
                ", nextPageToken='" + nextPageToken + '\'' +
                ", items=" + items +
                '}';
    }
}
