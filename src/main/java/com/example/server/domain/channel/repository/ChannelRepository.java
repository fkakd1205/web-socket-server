package com.example.server.domain.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.domain.channel.entity.ChannelEntity;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Integer> {
    
}
