package com.example.finalProject.repository;

import com.example.finalProject.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {
    List<Event> findByTitle(String title);
}
