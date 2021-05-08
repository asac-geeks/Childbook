package com.example.finalProject.controller;

import com.example.finalProject.entity.AppUser;
import com.example.finalProject.entity.Parent;
import com.example.finalProject.models.AuthRequest;
import com.example.finalProject.entity.TemporaryUser;
import com.example.finalProject.models.ParentResponse;
import com.example.finalProject.models.UpdateLocationRequest;
import com.example.finalProject.models.VerificationRequest;
import com.example.finalProject.repository.ParentRepository;
import com.example.finalProject.repository.TemporaryPostRepository;
import com.example.finalProject.repository.TemporaryUserRepository;
import com.example.finalProject.repository.UserRepository;
import com.example.finalProject.service.SendEmailService;
import com.example.finalProject.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



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

    @Autowired
    TemporaryPostRepository temporaryPostRepository;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authrequest) throws Exception {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(authrequest.getUserName(), authrequest.getPassword());
            System.out.println(usernamePasswordAuthenticationToken);
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            throw new Exception("Invalid username and password");
        }

        return jwtUtil.generateToken(authrequest.getUserName());
    }

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String name, String pass) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("childappadmain@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText("Your Child " + name + " is trying to create account in our web site, is you are accepting that use this link => http://localhost:8081/parentverification. to verification the account, enter you email with this password = >" + pass + ". you can use this pass word to accept you children events sure or add posts or chat with author children");
            System.out.println("sending message");
            emailSender.send(message);
            System.out.println("done");
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @PostMapping("/signup")
    public RedirectView signup(@RequestBody TemporaryUser temporaryUser) {
        LocalDate now=LocalDate.now();
        System.out.println("age now ");
        System.out.println(now.getYear()-temporaryUser.getDateOfBirth().getYear());
        if(now.getYear()-temporaryUser.getDateOfBirth().getYear()<18){
            try {
                if (userRepository.findByUserName(temporaryUser.getUsername()) == null) {
                    String serialNumber = (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10);
                    Parent parent = parentRepository.findByParentEmail(temporaryUser.getParentEmail());
                    if(parent != null){
                        serialNumber = parent.getPassword();
                    };
                    temporaryUser.setSerialNumber(serialNumber);
                    temporaryUserRepository.save(temporaryUser);
                    System.out.println("Saved");
                    sendSimpleMessage(temporaryUser.getParentEmail(), "Verification", temporaryUser.getUsername(), temporaryUser.getSerialNumber());
                } else {
                    return new RedirectView("/error?message=already used username");
                }
            } catch (Exception ex) {
                System.out.println(ex);
                return new RedirectView("/error?message=Used%username");
            }
        }
        return new RedirectView("/");
    }




    @PostMapping("/parentverification")
    public String parentVerification(@RequestBody VerificationRequest verificationRequest) {
        try {
            TemporaryUser temporaryUser = temporaryUserRepository.findByParentEmailAndSerialNumber(verificationRequest.getParentEmail(), verificationRequest.getSerialNumber());
            if (temporaryUser != null) {
                Parent parent = parentRepository.findByParentEmail(verificationRequest.getParentEmail());
                System.out.println(parent);
                System.out.println("parent");
                if(parent == null){
                    parent = new Parent(temporaryUser.getParentEmail(), temporaryUser.getSerialNumber());
                    parent.setUserName(temporaryUser.getUserName() + " Parent");
                }else {
                    parent.setParentPassword(temporaryUser.getSerialNumber());
                }
                parent = parentRepository.save(parent);
                AppUser appUser = new AppUser(temporaryUser.getUsername(), temporaryUser.getPassword(), temporaryUser.getParentEmail(), parent,temporaryUser.getDateOfBirth(),temporaryUser.getLocation());
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
    };

    @PostMapping("/loginAsParent")
    public String paren(@RequestBody AuthRequest authrequest) {
        try {
            System.out.println(authrequest.getUserName());
            System.out.println(authrequest.getPassword());
            Parent parent = parentRepository.findByUserNameAndParentPassword(authrequest.getUserName(), authrequest.getPassword());
            System.out.println(parent);
            if (parent != null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authrequest.getUserName(), authrequest.getPassword());
                System.out.println(usernamePasswordAuthenticationToken);
                authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                return jwtUtil.generateToken(authrequest.getUserName());
            } else {
                return "Wrong Email or Password";
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return "error";
        }
    };

    @GetMapping("/parentProfile")
    public ResponseEntity parentProfile() {
        try {
            System.out.println((SecurityContextHolder.getContext().getAuthentication()) ==null);
            if((SecurityContextHolder.getContext().getAuthentication()) != null){
                Parent parent = parentRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                ParentResponse parentResponse = new ParentResponse();
                for (AppUser appUser : parent.getAppUsers()){
                    parentResponse.getComments().addAll(appUser.getComments());
                    parentResponse.getPosts().addAll(appUser.getPosts());
                    parentResponse.getShares().addAll(appUser.getShares());
                }
                parentResponse.setParent(parent);
                return new ResponseEntity(parentResponse, HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity( HttpStatus.NOT_ACCEPTABLE);
    };


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

    @PutMapping("/profile")
    public ResponseEntity updateUser(@RequestBody AppUser user) {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                if (userDetails != null) {
                    System.out.println(user.toString());
                    userDetails.setUserName(user.getUserName());
                    userDetails.setPassword(user.getPassword());
                    userRepository.save(userDetails);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails.getUserName(), userDetails.getPassword());
                    System.out.println(usernamePasswordAuthenticationToken);
                    authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                    return new ResponseEntity(jwtUtil.generateToken(userDetails.getUserName()),HttpStatus.OK);
                }
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception n) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/user/{userName}")
    public ResponseEntity userByName(@PathVariable String userName) {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName(userName);
                System.out.println(userDetails.getEmail());
                return new ResponseEntity<AppUser>(userDetails,HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/allusers")
    public ResponseEntity allUsers() {
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                Iterable<AppUser> allUsers=userRepository.findAll();
                List users=new ArrayList();
                for(AppUser user: allUsers){
                   users.add(user.getUserName());                }
                return new ResponseEntity(users,HttpStatus.OK);
            }
        }catch (Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/userLocation")
    public ResponseEntity updateUserLocation(@RequestBody UpdateLocationRequest updateLocationRequest ){
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                AppUser userDetails = userRepository.findByUserName((SecurityContextHolder.getContext().getAuthentication()).getName());
                userDetails.setLocation(updateLocationRequest.getLocation());
                userRepository.save(userDetails);
                return new ResponseEntity("updated",HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateparent")
    public ResponseEntity updateParentProfile(@RequestBody Parent parentUpdate){
        try {
            if ((SecurityContextHolder.getContext().getAuthentication()) != null) {
                Parent parent = parentRepository.findByUserName((SecurityContextHolder.getContext().getAuthentication()).getName());
                parent.setParentPassword(parentUpdate.getPassword());
                parent.setUserName(parentUpdate.getUserName());
                parent.setParentEmail(parentUpdate.getParentEmail());
                parent = parentRepository.save(parent);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(parent.getUserName(), parent.getPassword());
                System.out.println(usernamePasswordAuthenticationToken);
                authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                return new ResponseEntity(jwtUtil.generateToken(parent.getUserName()),HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }



}