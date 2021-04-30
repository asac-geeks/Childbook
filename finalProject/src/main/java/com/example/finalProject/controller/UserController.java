package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Parent;
import com.example.finalProject.entity.UserStorage;
import com.example.finalProject.models.AuthRequest;
import com.example.finalProject.entity.TemporaryUser;
import com.example.finalProject.models.VerificationRequest;
import com.example.finalProject.repository.ParentRepository;
import com.example.finalProject.repository.TemporaryUserRepository;
import com.example.finalProject.repository.UserRepository;
import com.example.finalProject.repository.UserStorageRepository;
import com.example.finalProject.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.expression.Lists;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemporaryUserRepository temporaryUserRepository;

    @Autowired
    private ParentRepository parentRepository;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authrequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getUserName(), authrequest.getPassword()));
        }
        catch (Exception e) {
            throw new Exception("Invalid username and password");
        }

        return jwtUtil.generateToken(authrequest.getUserName());
    }


    @PostMapping("/signup")
    public RedirectView signup(@RequestBody TemporaryUser temporaryUser){
        try{
            System.out.println(temporaryUser);
            String serialNumber = (int)(Math.random()*10)+""+(int) (Math.random()*10)+(int) (Math.random()*10)+(int) (Math.random()*10)+ (int) (Math.random()*10)+(int) (Math.random()*10);
            temporaryUser.setSerialNumber(serialNumber);
            temporaryUserRepository.save(temporaryUser);

            // Create a mail sender
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("spring.mail.host");
            mailSender.setPort(2525);
            mailSender.setUsername("f7ebf7eb692c21");
            mailSender.setPassword("9354df9205dc26");

            // Create an email instance
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("childappadmain@gmail.com");
            mailMessage.setTo(temporaryUser.getParentEmail());
            mailMessage.setSubject("Parent Verification from childBook");
            mailMessage.setText("Use this Serial Number => "+ serialNumber+"to verify your child account;" );
            mailSender.send(mailMessage);;

        }catch (Exception ex){
            return new RedirectView("/error?message=Used%username");
        }
        return new RedirectView("/");
    };


    @PostMapping("/parentverification")
    public String parentVerification(@RequestBody VerificationRequest verificationRequest){
        try{
            TemporaryUser temporaryUser = temporaryUserRepository.findByParentEmailAndSerialNumber(verificationRequest.getParentEmail(),verificationRequest.getSerialNumber());
            if (temporaryUser != null){
                System.out.println(temporaryUser);
                Parent parent = new Parent(temporaryUser.getParentEmail(),temporaryUser.getSerialNumber());
                parent = parentRepository.save(parent);
                AppUser appUser = new AppUser(temporaryUser.getUsername(),temporaryUser.getPassword(),temporaryUser.getParentEmail(),parent);
                userRepository.save(appUser);
                temporaryUserRepository.delete(temporaryUser);
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUserName(), appUser.getPassword()));
                return jwtUtil.generateToken(appUser.getUserName());
            }else {
                return "Wrong Email or Password";
            }
        }catch (Exception ex){
            return "error";
        }
    };

//    @PostMapping("/signup")
//    public RedirectView signup(@RequestBody AppUser user){
//        try{
//            System.out.println(user);
//            userRepository.save(user);
//        }catch (Exception ex){
//            return new RedirectView("/error?message=Used%username");
//        }
//        return new RedirectView("/");
//    }

    @GetMapping("/profile")
    public Object profile(Principal p){
        try{
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                return userDetails;
            }
            return "login before";
        }catch (Exception ex){
            return new RedirectView("/error?message=Used%username");
        }
    }
}
