package com.piebin.binproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends GenericFilterBean {
    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = tokenProvider.resolveToken(httpServletRequest);

        if (!ObjectUtils.isEmpty(token))
            if (tokenProvider.validate(token))
                setAuthenticationByToken(token, response);
        chain.doFilter(request, response);
    }

    private void setAuthenticationByToken(String token, ServletResponse response) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), response);
    }
}
