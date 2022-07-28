package com.example.server.domain.chat_message.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.chat_message.dto.ChatMessageDto;
import com.example.server.domain.chat_message.server.ChatMessageService;
import com.example.server.domain.message.dto.Message;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ws/v1/chat")
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    /*
     * "/ws/v1/chat/message" 라는 경로로 메세지를 보내면 "/topic/message"를 구독하는 사용자들에게 모두 메세지를 뿌린다.
     * @SendTo("/topic/message")   // SendTo 1:n, SendToUser 1:1
     */
    @PostMapping("/message")
    public ResponseEntity<?> createChat(@RequestBody ChatMessageDto dto) {
        Message message = new Message();

        message.setData(chatMessageService.sendMesasge(dto));
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");
        message.setSocketMemo("새로운 메세지 도착!");

        simpMessagingTemplate.convertAndSend("/topic/message", message);

        return new ResponseEntity<>(message, message.getStatus());
    }

    /*
     * user의 id값이 파라미터로 넘어온 {id}와 동일한 유저에게 메세지 전달
     */
    @PostMapping("/message/{userId}")
    public ResponseEntity<?> createChatToUser(@PathVariable UUID userId) {
        Message message = new Message();

        message.setStatus(HttpStatus.OK);
        message.setMessage("success");
        message.setSocketMemo("깨우기 알림!");

        simpMessagingTemplate.convertAndSend("/topic/message/" + userId, message);

        return new ResponseEntity<>(message, message.getStatus());
    }

    /*
     * 파라미터로 넘어온 {channelId}와 동일한 채널에 접속한 유저에게 메세지 전달
     */
    @PostMapping("/message/channel/{channelId}")
    public ResponseEntity<?> createChatToChannel(@PathVariable UUID channelId, @RequestBody ChatMessageDto dto) {
        Message message = new Message();

        message.setData(chatMessageService.sendMesasge(dto));
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");
        message.setSocketMemo("깨우기 알림!");

        simpMessagingTemplate.convertAndSend("/topic/message/channel/" + channelId, message);

        return new ResponseEntity<>(message, message.getStatus());
    }
} 
