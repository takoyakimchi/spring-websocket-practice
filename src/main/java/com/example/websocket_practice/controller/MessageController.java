package com.example.websocket_practice.controller;

import com.example.websocket_practice.dto.MessageRequest;
import com.example.websocket_practice.dto.MessageResponse;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    private static final Set<String> bannedWords = Set.of("dislike", "hate", "despise");

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public MessageResponse sendMessage(MessageRequest message) {
        if (bannedWords.contains(message.content())) {
            throw new RuntimeException("You can't use that word.");
        }

        return new MessageResponse(message.username() + ": " + message.content());
    }

    @MessageExceptionHandler
    public void handleException(RuntimeException e) {
        log.info("Exception: ", e);
    }
}
