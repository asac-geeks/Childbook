package com.example.finalProject.controller;

import com.example.finalProject.entity.GamesApi;
import com.example.finalProject.repository.FavouriteGamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class GameController {

    @Autowired
    WebClient webClient;

    @Autowired
    FavouriteGamesRepository favouriteGamesRepository;

    @GetMapping("/games")
    public Flux<GamesApi> allGamesRoute(){
        String url = "https://www.freetogame.com/api/games";
        return  webClient.get().uri(url).retrieve().bodyToFlux(GamesApi.class);
    }

    @GetMapping("/games/category/{category}")
    public Flux<GamesApi> getGameByCategory(@PathVariable("category") String category){
        String url = "https://www.freetogame.com/api/games?category="+ category;
        return webClient.get().uri(url).retrieve().bodyToFlux(GamesApi.class);
    }

    @GetMapping("/games/{id}")
    public Mono<GamesApi> findById(@PathVariable("id") Integer id) {
        String url = "https://www.freetogame.com/api/game?id="+id;
        return  webClient.get().uri(url).retrieve().bodyToMono(GamesApi.class);
         /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
              clientResponse -> Mono.empty())*/
    }

    @PostMapping("/games/{id}")
    public Mono<GamesApi> addToFavourite(@PathVariable("id") Integer id) {
        String url = "https://www.freetogame.com/api/game?id="+id;
        Mono<GamesApi> gameMono = webClient.get().uri(url).retrieve().bodyToMono(GamesApi.class);
        GamesApi game = gameMono.block();
        favouriteGamesRepository.save(game);
        return gameMono;
         /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
              clientResponse -> Mono.empty())*/
    }
}