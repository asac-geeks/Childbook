package com.example.finalProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public SendEmailService() {
    }


    public void sendMail(String to,String body, String topic){
        try {
            SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setFrom("childappadmain@gmail.com");
            simpleMailMessage.setSubject(topic);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);
            System.out.println("sent");
        }catch (Exception ex){
            System.out.println(ex);
        }


    }
}
