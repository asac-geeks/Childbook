//package com.example.finalProject.service;
//
//import com.example.finalProject.models.Game;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Service
//public class ApiService {
//    private final WebClient webClient;
//
//    public MyService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("https://example.org").build();
//    }
//
//    public Mono<Game> someRestCall(String name) {
//        return this.webClient.get().uri("/{name}/details", name)
//                .retrieve().bodyToMono(Game.class);
//    }
//
//}
