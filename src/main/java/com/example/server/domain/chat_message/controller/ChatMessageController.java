package com.example.server.domain.chat_message.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.chat_message.dto.ChatMessageDto;
import com.example.server.domain.message.dto.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ws/v1/chat")
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    /*
     * "/ws/v1/chat/message" 라는 경로로 메세지를 보내면 "/topic/message"를 구독하는 사용자들에게 모두 메세지를 뿌린다.
     * @SendTo("/topic/message")   // SendTo 1:n, SendToUser 1:1
     * 
     */
    @PostMapping("/message")
    public ResponseEntity<?> createChat(@RequestBody ChatMessageDto dto) {
        Message message = new Message();

        message.setStatus(HttpStatus.OK);
        message.setMessage("success");
        message.setSocketMemo("새로운 메세지 도착!");

        simpMessagingTemplate.convertAndSend("/topic/message", message);

        return new ResponseEntity<>(message, message.getStatus());
    }
}
