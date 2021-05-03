package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Event;
import com.example.finalProject.entity.Groups;
import com.example.finalProject.entity.Post;
import com.example.finalProject.repository.EventRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class EventController {

    @Autowired
  EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;


  @PostMapping("/addevent/{id}")
  public RedirectView addevent(@RequestBody Event event
                               ){
    try{
      if((SecurityContextHolder.getContext().getAuthentication()) != null){
        AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        Event newEvent=new Event(userDetails,event.getBody(),event.getLocation(),event.getImageUrl(),event.getTitle());
        userDetails.events.add(newEvent);
        eventRepository.save(newEvent);
      }

    }catch (Exception ex){
      return new RedirectView("/error?message=Used%username");
    }
    return new RedirectView("/");
  }

  @GetMapping("/event/{id}")
  public ResponseEntity<Event> handleGetEvent(@RequestParam(value = "id") Integer id) {
    try{
      Event event = eventRepository.findById(id).get();
      return new ResponseEntity(event, HttpStatus.OK);
    }catch (Exception ex){
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
  }
}