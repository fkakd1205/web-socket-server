package com.example.server.domain.chat_message.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private String username;
    private String name;
    private String content;
    private LocalDateTime dateTime;
}
