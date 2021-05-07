package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Event;
import com.example.finalProject.entity.EventAttendees;
import com.example.finalProject.entity.Likes;
import com.example.finalProject.repository.EventAttendeesRepository;
import com.example.finalProject.repository.EventRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Set;

@RestController
public class EventAttendeesController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EventAttendeesRepository eventAttendeesRepository;

    @Autowired
    EventRepository eventRepository;

    @PostMapping("/attendEvent/{id}")
    public RedirectView attendEvent(@PathVariable Integer id) {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                System.out.println(userDetails);
                System.out.println("userDetails");
                EventAttendees eventAttendees = eventAttendeesRepository.save(new EventAttendees(userDetails, eventRepository.findById(id).get()));
                System.out.println("userDetails");
                return new RedirectView("/attendusers/"+ userDetails.getId());
            }

        } catch (Exception ex) {
            return new RedirectView("/error?message=Used%username");
        }
        return new RedirectView("/error?message=Used%username");
    }

    @DeleteMapping("/attendEvent/{id}")
    public ResponseEntity deleteAttendEvent(@PathVariable("id") Integer id) {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                eventAttendeesRepository.deleteById(id);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/attendusers/{id}")
    public ResponseEntity handleUsersFromSingleEvent(@PathVariable int id) {
        Event event = eventRepository.findById(id).get();
        try {
            if (event != null) {
                List<EventAttendees> attendeesUsers = event.getEventAttendees();
                return new ResponseEntity(attendeesUsers, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


}
