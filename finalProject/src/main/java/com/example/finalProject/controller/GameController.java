package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.GamesApi;
import com.example.finalProject.repository.FavouriteGamesRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    WebClient webClient;

    @Autowired
    FavouriteGamesRepository favouriteGamesRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/games")
    public Flux<GamesApi> allGamesRoute() {
        String url = "https://www.freetogame.com/api/games";
        return webClient.get().uri(url).retrieve().bodyToFlux(GamesApi.class);
    }

    @GetMapping("/games/category/{category}")
    public Flux<GamesApi> getGameByCategory(@PathVariable("category") String category) {
        String url = "https://www.freetogame.com/api/games?category=" + category;
        return webClient.get().uri(url).retrieve().bodyToFlux(GamesApi.class);
    }

    @GetMapping("/games/{id}")
    public Mono<GamesApi> findById(@PathVariable("id") Integer id) {
        String url = "https://www.freetogame.com/api/game?id=" + id;
        return webClient.get().uri(url).retrieve().bodyToMono(GamesApi.class);
         /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
              clientResponse -> Mono.empty())*/
    }

    @PostMapping("/games/{id}")
    public Mono<GamesApi> addToFavourite(@PathVariable("id") Integer id) {
        String url = "https://www.freetogame.com/api/game?id=" + id;
        Mono<GamesApi> gameMono = webClient.get().uri(url).retrieve().bodyToMono(GamesApi.class);
        if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
            AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            GamesApi game = gameMono.block();
            game.setUser(userDetails);
            favouriteGamesRepository.save(game);
        }
        return gameMono;
    }

    //==========================salah
    @DeleteMapping("/deletefavgame/{id}")
    public void deleteGame(@PathVariable int id) {
        AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        GamesApi deletedGame = favouriteGamesRepository.findById(id).get();
        if (deletedGame.getUser().getId() == userDetails.getId()) {
            favouriteGamesRepository.deleteById(id);
        }
    }
    //==========================salah

}