package com.example.finalProject.models.youtube;

public class YouTubeThumbnailsHigh {
     String url;
     String width;
     String height;

    public YouTubeThumbnailsHigh(){}
    public YouTubeThumbnailsHigh(String url, String width, String height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "YouTubeThumbnailsHigh{" +
                "url='" + url + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
