package com.example.websocket_practice.controller;

import com.example.websocket_practice.dto.MessageResponse;
import com.example.websocket_practice.dto.MessageRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public MessageResponse greeting(MessageRequest message) throws Exception {
//        Thread.sleep(1000); // simulated delay
        return new MessageResponse(HtmlUtils.htmlEscape(message.username()) + ": " + HtmlUtils.htmlEscape(message.content()));
    }
}
