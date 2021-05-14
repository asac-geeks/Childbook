package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.GamesApi;
import com.example.finalProject.repository.FavouriteGamesRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin(origins= "*")
public class GameController {

    @Autowired
    WebClient webClient;

    @Autowired
    FavouriteGamesRepository favouriteGamesRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/games")
    public ResponseEntity allGamesRoute(){
        String url = "https://www.freetogame.com/api/games";
          try{
              Flux<GamesApi>  games = webClient.get().uri(url).retrieve().bodyToFlux(GamesApi.class);
              return new ResponseEntity(games,HttpStatus.OK);
          }catch (WebClientException ex){
              return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
          }
    }

    @GetMapping("/games/category/{category}")
    public ResponseEntity<GamesApi> getGameByCategory(@PathVariable("category") String category){
        String url = "https://www.freetogame.com/api/games?category="+ category;
        try{
            Flux<GamesApi>  games = webClient.get().uri(url).retrieve().bodyToFlux(GamesApi.class);
            return new ResponseEntity(games,HttpStatus.OK);
        }catch (WebClientException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<GamesApi> findById(@PathVariable("id") Integer id) {
        String url = "https://www.freetogame.com/api/game?id="+id;
        try{
            GamesApi game = webClient.get().uri(url).retrieve().bodyToMono(GamesApi.class).block();
            return new ResponseEntity<>(game, HttpStatus.OK);
        }catch (WebClientException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

    @DeleteMapping("/games/{id}")
    public ResponseEntity deleteAGame(@PathVariable Integer id){

        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                favouriteGamesRepository.deleteById(id);
            }
            return new ResponseEntity("/games", HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}