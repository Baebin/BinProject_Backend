package com.piebin.binproject.security;

import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_HEADER_COOKIE = "Authorization";

    private final SecurityAccountService securityAccountService;

    @Value("${spring.security.key.token.secret}")
    private String secretKey;

    private final long tokenValidityInMilliseconds = 2 * 60 * 60 * 1000L;

    @Override
    public void afterPropertiesSet() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(String id) {
        Claims claims = Jwts.claims()
                .subject(id)
                .build();
        Date createdDate = new Date();
        Date expiredDate = new Date(createdDate.getTime() + tokenValidityInMilliseconds);
        return Jwts.builder()
                .claims(claims)
                .issuedAt(createdDate)
                .expiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getId(String token) {
        if (token.isEmpty())
            return null;
        return Jwts.parser().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = securityAccountService.loadUserByUsername(getId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            Optional<String> token = Arrays.stream(cookies)
                    .filter((cookie -> cookie.getName().equals(AUTHORIZATION_HEADER) || cookie.getName().equals(AUTHORIZATION_HEADER_COOKIE)))
                    .map((cookie -> cookie.getValue()))
                    .findFirst();
            if (token.isPresent())
                return token.get();
        }
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    public boolean validate(String token) {
        try {
            getId(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다. " + token);
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다. " + token);
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다. " + token);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다. " + token);
        }
        return false;
    }
}
