package com.example.finalProject.models.youtube;

public class YouTubeItemsSnippet {
    String publishedAt;
    String  title;
    String description;
    String channelTitle;  // in case we need it
    YouTubeThumbnails thumbnails;

    public YouTubeThumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(YouTubeThumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public YouTubeItemsSnippet(){}
    public YouTubeItemsSnippet(String publishedAt, String title, String description, String channelTitle) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.channelTitle = channelTitle;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    @Override
    public String toString() {
        return "YouTubeItemsSnippet{" +
                "publishedAt='" + publishedAt + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", channelTitle='" + channelTitle + '\'' +
                ", thumbnails=" + thumbnails +
                '}';
    }
}
