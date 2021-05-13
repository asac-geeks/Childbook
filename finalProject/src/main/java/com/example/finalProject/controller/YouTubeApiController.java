package com.example.finalProject.controller;

import com.example.finalProject.models.youtube.YouTubeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin(origins= "*")
public class YouTubeApiController {
    String API_KEY = "AIzaSyCl2DUwe_iYExrnevvbsjps2nf2ceTAmo8";
    @Autowired
    WebClient webClient;

    @GetMapping("/videos/{query}")
    public Flux<YouTubeApi> getVideoList(@PathVariable("query")String query){
//        String API_KEY = "AIzaSyCl2DUwe_iYExrnevvbsjps2nf2ceTAmo8";
        String url = "https://youtube.googleapis.com/youtube/v3/search?key="+ API_KEY+"&q="+query+"&safeSearch=strict&part=snippet&type=video";

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(YouTubeApi.class);
    }

    @GetMapping("/videos/childrenStories")
    public Flux<YouTubeApi> getStoriesVideo(){

        String url = "https://youtube.googleapis.com/youtube/v3/search?key="+ API_KEY+"&q=قصص اطفال&safeSearch=strict&part=snippet&type=video";
       return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(YouTubeApi.class);
    }

    @GetMapping("/videos/prophetsStories")
    public Flux<YouTubeApi> getProphetsStoriesList(){
        String url = "https://youtube.googleapis.com/youtube/v3/search?key="+ API_KEY+"&q=قصص الانبياء&safeSearch=strict&part=snippet&type=video";
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(YouTubeApi.class);
    }

}
