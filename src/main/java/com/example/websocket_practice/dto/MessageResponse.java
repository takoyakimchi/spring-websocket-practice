package com.example.websocket_practice.dto;

public class MessageResponse {

    private String content;

    public MessageResponse() {
    }

    public MessageResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
