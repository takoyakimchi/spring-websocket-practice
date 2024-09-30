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

    @MessageMapping("/hello") //  /app/hello 경로로 publish 해야 메시지가 보내짐
    @SendTo("/topic/greetings")  //  /topic/greetings 를 subscribe 하고 있는 사람들에게 메시지가 보내짐
    public MessageResponse greeting(MessageRequest message) {
        // HtmlUtils.htmlEscape는 XSS를 예방하기 위해 있는 메서드임

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
