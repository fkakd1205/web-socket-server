package com.example.server.domain.chat_message.server;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.server.domain.chat_message.dto.ChatMessageDto;
import com.example.server.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final UserService userSerivce;
    
    public ChatMessageDto sendMesasge(ChatMessageDto dto) {
        ChatMessageDto messageDto = ChatMessageDto.builder()
            .username(userSerivce.getUserName())
            .name(userSerivce.getName())
            .content(dto.getContent())
            .dateTime(LocalDateTime.now())
            .build();

        return messageDto;
    }
}
