package com.example.server.domain.channel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.channel.service.ChannelService;
import com.example.server.domain.message.dto.Message;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channel")
public class ChannelContoller {
    private final ChannelService channelService;

    @GetMapping("")
    public ResponseEntity<?> searchList() {
        Message message = new Message();

        message.setData(channelService.searchList());
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");

        return new ResponseEntity<>(message, message.getStatus());
    }
}
