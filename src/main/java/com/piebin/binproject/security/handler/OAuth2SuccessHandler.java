package com.piebin.binproject.security.handler;

import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.dto.account.AccountLoginDetailDto;
import com.piebin.binproject.security.SecurityOAuth2User;
import com.piebin.binproject.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SecurityOAuth2User user = (SecurityOAuth2User) authentication.getPrincipal();

        Account account = user.getAccount();

        String id = account.getId();
        String token = tokenProvider.createAccessToken(id);

        AccountLoginDetailDto dto = AccountLoginDetailDto.toDto(account, token);
        String url = "http://piebin.kro.kr/login/redirect/"
                + dto.getToken() + "/"
                + dto.getIdx() + "/"
                + dto.getName() + "/"
                + dto.getPermission();
        getRedirectStrategy().sendRedirect(request, response, url);
    }
}
