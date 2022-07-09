package com.example.server.config.auth;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.example.server.config.utils.AuthTokenUtils;
import com.example.server.domain.user.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                System.out.println("===== Authorization Filter =====");
                
                Cookie jwtTokenCookie = WebUtils.getCookie(request, "access_token");

                // 액세스 토큰 쿠키가 있는지 확인, 만약에 없다면 체인을 계속 타게하고 있다면 검증한다.
                if (jwtTokenCookie == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                String accessToken = jwtTokenCookie.getValue();
                Claims claims = null;

                try {
                    claims = Jwts.parser().setSigningKey(AuthTokenUtils.getAccessTokenSecret()).parseClaimsJws(accessToken).getBody();
                } catch (Exception e) {
                    filterChain.doFilter(request, response);
                    return;
                }

                if(claims == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                UUID id = UUID.fromString(claims.get("id").toString());
                String username = claims.get("username").toString();

                UserEntity userEntity = UserEntity.builder()
                        .id(id)
                        .username(username)
                        .build();

                this.saveAuthenticationToSecurityContextHolder(userEntity);
                filterChain.doFilter(request, response);
            }

    private void saveAuthenticationToSecurityContextHolder(UserEntity userEntity) {
        UserDetails userDetails = userEntity;

        // Authentication 객체 생성해 SecurityContextHolder에 저장
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
