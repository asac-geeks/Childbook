package com.example.finalProject.controller;


import com.example.finalProject.entity.*;
import com.example.finalProject.repository.GroupAttendeesRepository;
import com.example.finalProject.repository.GroupRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Set;

@RestController
public class GroupAttendeesController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupAttendeesRepository groupAttendeesRepository;

    @Autowired
    GroupRepository groupRepository;


    @PostMapping("/attendGroup/{id}")
    public ResponseEntity<GroupAttendees> attendGroup(@PathVariable Integer id){
        GroupAttendees groupAttendees = new GroupAttendees();
        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                groupAttendees =  groupAttendeesRepository.save(new GroupAttendees(userDetails,groupRepository.findById(id).get()));
            }

        }catch (Exception ex){
            return new ResponseEntity(groupAttendees, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/attendGroup/{id}")
    public ResponseEntity deleteAteendGroup(@PathVariable("id") Integer id){

            try{
                groupAttendeesRepository.deleteById(id);
            }catch (IllegalArgumentException argumentException){
              return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/attendgroups/{id}")
    public ResponseEntity handleUsersFromSingleGroup(@PathVariable int id) {
        Groups group = groupRepository.findById(id).get();
        try {
            if (group != null) {
                Set<GroupAttendees> attendeesUsers = (Set<GroupAttendees>) group.getGroupAttendees();
                return new ResponseEntity(attendeesUsers, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
