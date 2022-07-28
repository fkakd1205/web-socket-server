package com.example.server.domain.channel.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.server.domain.channel.entity.ChannelEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ChannelDto {
    private UUID id;
    private String title;
    private LocalDateTime createdAt;

    public static ChannelDto toDto(ChannelEntity entity) {
        ChannelDto dto = ChannelDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .createdAt(entity.getCreatedAt())
            .build();

        return dto;
    }
}
