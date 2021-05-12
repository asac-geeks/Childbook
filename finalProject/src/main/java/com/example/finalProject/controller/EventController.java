package com.example.finalProject.controller;

import com.example.finalProject.entity.*;
import com.example.finalProject.models.VerificationRequest;
import com.example.finalProject.repository.EventRepository;
import com.example.finalProject.repository.ParentRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins= "*")
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/events")
    public ResponseEntity getAllEvent() {
        try {

            System.out.println(eventRepository.findAll());
            return new ResponseEntity(eventRepository.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/addevent")
    public RedirectView addevent(@RequestBody Event event) {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                Event newEvent = new Event(userDetails, event.getBody(), event.getLocation(), event.getImageUrl(), event.getTitle());
                userDetails.events.add(newEvent);
                eventRepository.save(newEvent);
            }

        } catch (Exception ex) {
            return new RedirectView("/error?message=Used%username");
        }
        return new RedirectView("/events");
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Event> handleGetEvent(@PathVariable(value = "id") Integer id) {
        try {
            Event event = eventRepository.findById(id).get();
            return new ResponseEntity(event, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/event/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer id, @RequestBody Event event) {
        Event event1 = eventRepository.findById(id).get();
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

                if (event1 != null && event1.getAppUser().getId() == userDetails.getId()) {
                    event1.setBody(event.getBody());
                    event1.setLocation(event.getLocation());
                    event1.setImageUrl(event.getImageUrl());
                    event1.setTitle(event.getTitle());
                    eventRepository.save(event1);
                } else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }

        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(event1, HttpStatus.OK);
    }


    @DeleteMapping("/event/{id}")
    public RedirectView deleteEvent(@PathVariable Integer id) {
        Event event1 = eventRepository.findById(id).get();
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

                if (event1 != null && event1.getAppUser().getId() == userDetails.getId()) {
                    eventRepository.delete(event1);
                }
            }

        } catch (Exception ex) {
            return new RedirectView("/error?message=Used%username");
        }
        return new RedirectView("/events");
    }

    @GetMapping("/userevents")
    public ResponseEntity renderEventFromUser() {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                if (userDetails.getEvents() != null) {
                    List<Event> userEvents = userDetails.getEvents();
                    return new ResponseEntity(userEvents, HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/eventbytitle/{title}")
    public ResponseEntity getEventByTitle(@PathVariable String title) {
        try {
            return new ResponseEntity(eventRepository.findByTitle(title), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}