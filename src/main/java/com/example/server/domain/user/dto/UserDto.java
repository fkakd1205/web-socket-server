package com.example.server.domain.user.dto;

import java.util.UUID;

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
public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private String name;
}
