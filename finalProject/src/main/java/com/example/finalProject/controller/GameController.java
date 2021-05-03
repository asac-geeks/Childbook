package com.example.finalProject.controller;

import com.example.finalProject.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class GameController {


    @Autowired
    WebClient webClient;

    @GetMapping("/games")
    public Flux<Game> allGamesRoute(){
        String url = "https://www.freetogame.com/api/games";
        return  webClient.get().uri(url).retrieve().bodyToFlux(Game.class);
    }

    @GetMapping("/games/category/{category}")
    public Flux<Game> getGameByCategory(@PathVariable("category") String category){
        String url = "https://www.freetogame.com/api/games?category="+ category;
        return webClient.get().uri(url).retrieve().bodyToFlux(Game.class);

    }


    @GetMapping("/games/{id}")
    public Mono<Game> findById(@PathVariable("id") Integer id) {
        String url = "https://www.freetogame.com/api/game?id="+id;
        return webClient.get().uri(url).retrieve().bodyToMono(Game.class);
         /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
              clientResponse -> Mono.empty())*/
    }

}
