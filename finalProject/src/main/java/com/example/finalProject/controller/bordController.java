package com.example.finalProject.controller;

import com.example.finalProject.models.AxisModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class bordController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/bord/{to}")
    public void drow(@DestinationVariable String to, AxisModel axisModel) {
        System.out.println("handling send message: " + axisModel + " to: " + to);
        System.out.println("hellllllllooooooooooooooooooo");
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, axisModel);
    }
}
