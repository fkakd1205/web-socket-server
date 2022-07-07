package com.example.server.domain.user.dto;

import java.util.UUID;

import com.example.server.domain.user.entity.UserEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserNameDto {
    private UUID id;
    private String name;

    public static UserNameDto toDto(UserEntity entity) {
        UserNameDto dto = UserNameDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();

        return dto;
    }
}
