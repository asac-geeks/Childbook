package com.example.finalProject.models;

public class AxisModel {
    private String x;
    private String y;
    private String stroke_color;
    private String stroke_width;
    private String event;

    public String getX() {
        return this.x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return this.y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getStroke_color() {
        return stroke_color;
    }

    public void setStroke_color(String stroke_color) {
        this.stroke_color = stroke_color;
    }

    public String getStroke_width() {
        return stroke_width;
    }

    public void setStroke_width(String stroke_width) {
        this.stroke_width = stroke_width;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "AxisModel{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", stroke_color='" + stroke_color + '\'' +
                ", stroke_width='" + stroke_width + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
