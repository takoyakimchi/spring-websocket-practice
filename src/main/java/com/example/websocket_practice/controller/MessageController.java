package com.example.websocket_practice.controller;

import com.example.websocket_practice.dto.MessageRequest;
import com.example.websocket_practice.dto.MessageResponse;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public MessageResponse greeting(MessageRequest message) {
        if (message.content().equals("error")) {
            throw new RuntimeException("You can't use that word.");
        }

        return new MessageResponse(
                true,
                HtmlUtils.htmlEscape(message.username()) + ": " + HtmlUtils.htmlEscape(message.content())
        );
    }

    @MessageExceptionHandler
    @SendTo("/topic/greetings")
    public MessageResponse handleException(RuntimeException e) {
        return new MessageResponse(false, e.getMessage());
    }
}
