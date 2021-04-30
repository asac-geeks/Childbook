package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.UserStorage;
import com.example.finalProject.repository.UserRepository;
import com.example.finalProject.repository.UserStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RestController
@CrossOrigin
public class UserChatController {
    @Autowired
    private UserStorageRepository userStorageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration/{userName}")
    public ResponseEntity<Void> register(@PathVariable String userName) {
        System.out.println("handling register user request: " + userName);
        try {
            Iterator<UserStorage> userStorage = userStorageRepository.findAll().iterator();
            System.out.println(userStorage);
            if(userStorage == null || (userStorage != null && userStorage.hasNext())){
                userStorageRepository.save(new UserStorage());
            }
            AppUser appUser = userRepository.findByUserName(userName);
            if (appUser != null){
                appUser.setUserStorage((UserStorage)((ArrayList) userStorageRepository.findAll()).get(0));
                userRepository.save(appUser);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        Set<String> usernames = new HashSet();
        UserStorage userStorage = (UserStorage)((ArrayList) userStorageRepository.findAll()).get(0);
        if(userStorage != null && userStorage.getAppUsers().size() != 0){
            for(AppUser appUser :  userStorage.getAppUsers()){
                usernames.add(appUser.getUserName());
            }
        }

        return usernames;
    }

}
