package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Event;
import com.example.finalProject.entity.Groups;
import com.example.finalProject.entity.Post;
import com.example.finalProject.repository.GroupRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Set;

@RestController
public class GroupController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @PostMapping("/addgroup")
    public ResponseEntity<Groups> addGroup(@RequestBody Groups group){
        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                group.setAppUser(userDetails);
                group = groupRepository.save(group);
            }

        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
            return new ResponseEntity(group, HttpStatus.OK);
    };

    @GetMapping("/usergroups")
    public ResponseEntity handleGroupsFromUser() {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                if (userDetails.getGroups() != null) {
                    Set<Groups> userGroups = userDetails.getGroups();
                    return new ResponseEntity(userGroups, HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
