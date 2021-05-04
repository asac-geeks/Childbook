package com.example.finalProject.repository;

import com.example.finalProject.models.GamesApi;
import org.springframework.data.repository.CrudRepository;

public interface FavouriteGamesRepository extends CrudRepository<GamesApi,Integer> {
}
