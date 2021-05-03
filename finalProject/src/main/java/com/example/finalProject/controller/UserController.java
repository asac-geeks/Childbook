package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Parent;
import com.example.finalProject.models.AuthRequest;
import com.example.finalProject.entity.TemporaryUser;
import com.example.finalProject.models.VerificationRequest;
import com.example.finalProject.repository.ParentRepository;
import com.example.finalProject.repository.TemporaryUserRepository;
import com.example.finalProject.repository.UserRepository;
import com.example.finalProject.service.SendEmailService;
import com.example.finalProject.util.JwtUtil;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@RestController
public class UserController {
    @Autowired
    SendEmailService sendEmailService;

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
        } catch (Exception e) {
            throw new Exception("Invalid username and password");
        }

        return jwtUtil.generateToken(authrequest.getUserName());
    }

    @PostMapping("/signup")
    public RedirectView signup(@RequestBody TemporaryUser temporaryUser) {
        try {
            temporaryUser.setUsername("hossam");
            temporaryUser.setPassword("123456");
            temporaryUser.setParentEmail("sam@gmail.com");
            System.out.println(temporaryUser);
            String serialNumber = (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10);
            temporaryUser.setSerialNumber(serialNumber);
            temporaryUserRepository.save(temporaryUser);
            sendEmailService.sendMail("areej.obaid@yahoo.com", "hiiii", "hellow");
//            // Create a mail sender
//            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//            mailSender.setHost("spring.mail.host");
//            mailSender.setPort(2525);
//            mailSender.setUsername("f7ebf7eb692c21");
//            mailSender.setPassword("9354df9205dc26");
//
//            // Create an email instance
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setFrom("childappadmain@gmail.com");
//            mailMessage.setTo(temporaryUser.getParentEmail());
//            mailMessage.setSubject("Parent Verification from childBook");
//            mailMessage.setText("Use this Serial Number => "+ serialNumber+"to verify your child account;" );
//            mailSender.send(mailMessage);;

        } catch (Exception ex) {
            return new RedirectView("/error?message=Used%username");
        }
        return new RedirectView("/");
    }

    ;


    @PostMapping("/parentverification")
    public String parentVerification(@RequestBody VerificationRequest verificationRequest) {
        try {
            TemporaryUser temporaryUser = temporaryUserRepository.findByParentEmailAndSerialNumber(verificationRequest.getParentEmail(), verificationRequest.getSerialNumber());
            if (temporaryUser != null) {
                System.out.println(temporaryUser);
                Parent parent = new Parent(temporaryUser.getParentEmail(), temporaryUser.getSerialNumber());
                parent = parentRepository.save(parent);
                AppUser appUser = new AppUser(temporaryUser.getUsername(), temporaryUser.getPassword(), temporaryUser.getParentEmail(), parent);
                userRepository.save(appUser);
                temporaryUserRepository.delete(temporaryUser);
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUserName(), appUser.getPassword()));
                return jwtUtil.generateToken(appUser.getUserName());
            } else {
                return "Wrong Email or Password";
            }
        } catch (Exception ex) {
            return "error";
        }
    }

    ;

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
    public Object profile(Principal p) {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                return userDetails;
            }
            return "login before";
        } catch (Exception ex) {
            return new RedirectView("/error?message=Used%username");
        }
    }

    //.............................................AppUser..............................................
    /*
     *  API youtube call + strict for kids
     *  Update user
     *      1. insert parent
     *      2. insert app_user
     * */
    // update child data
    @PutMapping("/profile")
    public ResponseEntity updateUser(@RequestBody AppUser user) {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                if (userDetails != null) {
                    System.out.println(user.toString());
                    userDetails.setUserName(user.getUserName());
                    userDetails.setEmail(user.getEmail());
                    userDetails.setPassword(user.getPassword());
                      userRepository.save(userDetails);
                }
            }
            return new ResponseEntity<AppUser>(HttpStatus.OK);
        } catch (Exception n) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}