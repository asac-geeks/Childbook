package com.example.finalProject.entity;
import com.example.finalProject.entity.AppUser;

import javax.persistence.*;

@Entity
public class GamesApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String title;
    String thumbnail;
    String short_description;
    String description;
    String game_url;
    String genre;
    String platform;
    String release_date;
    String freetogame_profile_url;
    @ManyToOne
    AppUser user;

    public GamesApi(){}

    public GamesApi(String title, String thumbnail, String short_description, String description,  String game_url, String genre, String platform, String release_date, String freetogame_profile_url, AppUser appUser) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.short_description = short_description;
        this.description = description;
        this.game_url = game_url;
        this.genre = genre;
        this.platform = platform;
        this.release_date = release_date;
        this.freetogame_profile_url = freetogame_profile_url;
        this.user = appUser;
    }
    public Integer getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getGame_url() {
        return game_url;
    }

    public void setGame_url(String game_url) {
        this.game_url = game_url;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getFreetogame_profile_url() {
        return freetogame_profile_url;
    }

    public void setFreetogame_profile_url(String freetogame_profile_url) {
        this.freetogame_profile_url = freetogame_profile_url;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", short_description='" + short_description + '\'' +
                ", game_url='" + game_url + '\'' +
                ", genre='" + genre + '\'' +
                ", platform='" + platform + '\'' +
                ", release_date='" + release_date + '\'' +
                ", freetogame_profile_url='" + freetogame_profile_url + '\'' +
                '}';
    }

}
