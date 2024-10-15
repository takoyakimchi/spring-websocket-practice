package com.example.websocket_practice.controller;

import com.example.websocket_practice.dto.ChatMessageRequest;
import com.example.websocket_practice.dto.ChatMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessageResponse sendMessage(ChatMessageRequest request) {
        return new ChatMessageResponse(request.username(), request.content());
    }

    @MessageExceptionHandler
    public void handleException(RuntimeException e) {
        log.info("Exception: ", e);
    }
}
