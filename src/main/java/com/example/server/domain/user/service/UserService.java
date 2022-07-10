package com.example.server.domain.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.server.config.utils.AuthTokenUtils;
import com.example.server.domain.exception.CustomInvalidUserException;
import com.example.server.domain.user.dto.UserLoginReqDto;
import com.example.server.domain.user.dto.UserNameDto;
import com.example.server.domain.user.entity.UserEntity;
import com.example.server.domain.user.respository.UserRepository;
import com.example.server.utils.CustomCookieInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public List<UserNameDto> searchList() {
        List<UserEntity> entities = userRepository.findAll();
        List<UserNameDto> dtos = entities.stream().map(r -> UserNameDto.toDto(r)).collect(Collectors.toList());
        return dtos;
    }

    public void login(HttpServletResponse response, UserLoginReqDto dto) {
        String USERNAME = dto.getUsername();
        String PASSWORD = dto.getPassword();

        Optional<UserEntity> entityOpt = userRepository.findByUsername(USERNAME);

        if(entityOpt.isPresent()) {
            UserEntity savedUserEntity = entityOpt.get();
            // 로그인 성공한다면 access_token, refresh_token 생성
            if(savedUserEntity.getPassword().equals(PASSWORD)) {
                UUID REFRESH_TOKEN_ID = UUID.randomUUID();

                // acccess token, refresh token 생성
                String accessToken = AuthTokenUtils.getJwtAccessToken(savedUserEntity, REFRESH_TOKEN_ID);

                // ac_token 쿠키 생성
                ResponseCookie tokenCookie = ResponseCookie.from("access_token", accessToken)
                    .httpOnly(true)
                    .domain(CustomCookieInterface.COOKIE_DOMAIN)
                    .secure(CustomCookieInterface.SECURE)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(CustomCookieInterface.JWT_TOKEN_COOKIE_EXPIRATION)
                    .build();

                response.addHeader(HttpHeaders.SET_COOKIE , tokenCookie.toString());
                return;
            }
        }
        throw new CustomInvalidUserException("login error.");
    }

    public String getUserName() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getUsername();
    }

    public String getName() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity = userRepository.findByUsername(userDetails.getUsername());
        return userEntity.get().getName();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("login error."));
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
