package com.example.finalProject.repository;

import com.example.finalProject.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
}
