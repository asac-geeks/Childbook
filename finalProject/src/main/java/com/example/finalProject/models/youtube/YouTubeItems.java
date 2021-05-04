package com.example.finalProject.models.youtube;

// https://www.youtube.com/embed/id.getVideoId();
public class YouTubeItems {
     String kind;
     String etag;
     YouTubeItemId id;
     YouTubeItemsSnippet snippet;
     public  YouTubeItems(){}
     public YouTubeItems(String kind, String etag,YouTubeItemId id, YouTubeItemsSnippet snippet ) {
          this.kind = kind;
          this.etag = etag;
          this.id = id;
          this.snippet = snippet;
     }

     public YouTubeItemsSnippet getSnippet() {
          return snippet;
     }

     public void setSnippet(YouTubeItemsSnippet snippet) {
          this.snippet = snippet;
     }

     public YouTubeItemId getId() {
          return id;
     }

     public void setId(YouTubeItemId id) {
          this.id = id;
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
          return "YouTubeItems{" +
                  "kind='" + kind + '\'' +
                  ", etag='" + etag + '\'' +
                  ", id=" + id +
                  ", snippet=" + snippet +
                  '}';
     }
}
