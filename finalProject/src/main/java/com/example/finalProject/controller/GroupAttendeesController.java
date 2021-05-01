package com.example.finalProject.controller;


import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.EventAttendees;
import com.example.finalProject.entity.GroupAttendees;
import com.example.finalProject.repository.GroupAttendeesRepository;
import com.example.finalProject.repository.GroupRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class GroupAttendeesController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupAttendeesRepository groupAttendeesRepository;

    @Autowired
    GroupRepository groupRepository;


    @PostMapping("/attendGroup/{id}")
    public RedirectView signup(@PathVariable Integer id){
        try{
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                GroupAttendees groupAttendees =  groupAttendeesRepository.save(new GroupAttendees(userDetails,groupRepository.findById(id).get()));
            }

        }catch (Exception ex){
            return new RedirectView("/error?message=Used%username");
        }
        return new RedirectView("/");
    };
}
