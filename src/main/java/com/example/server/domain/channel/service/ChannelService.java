package com.example.server.domain.channel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.server.domain.channel.dto.ChannelDto;
import com.example.server.domain.channel.entity.ChannelEntity;
import com.example.server.domain.channel.repository.ChannelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;

    public List<ChannelDto> searchList() {
        List<ChannelEntity> entities = channelRepository.findAll();
        List<ChannelDto> dtos = entities.stream().map(r -> ChannelDto.toDto(r)).collect(Collectors.toList());
        return dtos;
    }
}
