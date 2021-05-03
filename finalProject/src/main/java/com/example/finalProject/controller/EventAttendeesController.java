package com.example.finalProject.controller;
import com.example.finalProject.entity.AppUser;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class EventAttendeesController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EventAttendeesRepository eventAttendeesRepository;

    @Autowired
    EventRepository eventRepository;

    @PostMapping("/attendEvent/{id}")
    public RedirectView attendEvent(@PathVariable Integer id){
        try{
            if((SecurityContextHolder.getContext().getAuthentication())!= null){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                EventAttendees eventAttendees =  eventAttendeesRepository.save(new EventAttendees(userDetails,eventRepository.findById(id).get()));
            }

        }catch (Exception ex){
            return new RedirectView("/error?message=Used%username");
        }
        return new RedirectView("/");
    }

    @DeleteMapping("/attendEvent/{id}")
    public ResponseEntity deleteAttendEvent(@PathVariable("id")Integer id){
        try{
            eventAttendeesRepository.deleteById(id);
        }catch (NullPointerException ex)
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);

    }
}
