package com.example.finalProject.controller;

import com.example.finalProject.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
public class ApisController {

    @Autowired
    WebClient.Builder webClientBuilder;


    @GetMapping("/games")
    public Flux<Game> allGamesRoute(){
        String url = "https://www.freetogame.com/api/games";

       return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Game.class);
    }

//    @GetMapping("/games/{}")
}
