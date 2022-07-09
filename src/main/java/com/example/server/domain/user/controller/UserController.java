package com.example.server.domain.user.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.domain.message.dto.Message;
import com.example.server.domain.user.dto.UserDto;
import com.example.server.domain.user.dto.UserLoginReqDto;
import com.example.server.domain.user.entity.UserEntity;
import com.example.server.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<?> searchList() {
        Message message = new Message();

        message.setData(userService.searchList());
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");

        return new ResponseEntity<>(message, message.getStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse response, @RequestBody UserLoginReqDto dto) {
        Message message = new Message();

        userService.login(response, dto);
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");

        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/login-check")
    public ResponseEntity<?> loginCheck(){
        Message message = new Message();

        // Security Filter 에서 넘어오는 SecurityContextHolder 를 읽어서 유저상태를 검사한다.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 로그인 유저 - UserDetails타입
        // 비로그인 유저 - String타입("anonymousUser")
        if(principal instanceof UserDetails) {
            UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDto userDto = UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .build();

            message.setStatus(HttpStatus.OK);
            message.setMessage("loged");
            message.setMemo("already login");
            message.setData(userDto);
        } else {
            message.setStatus(HttpStatus.OK);
            message.setMessage("need_login");
            message.setMemo("need login");
        }
        return new ResponseEntity<>(message, message.getStatus());
    }

}
