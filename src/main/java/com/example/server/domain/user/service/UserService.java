package com.example.server.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.server.domain.user.dto.UserNameDto;
import com.example.server.domain.user.entity.UserEntity;
import com.example.server.domain.user.respository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserNameDto> searchList() {
        List<UserEntity> entities = userRepository.findAll();
        List<UserNameDto> dtos = entities.stream().map(r -> UserNameDto.toDto(r)).collect(Collectors.toList());
        return dtos;
    }
}
