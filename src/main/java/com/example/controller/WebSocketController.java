package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String msg(String msg)  {

        log.info("msg:{}", msg);
        return "server：Hello " + msg;
    }





}
