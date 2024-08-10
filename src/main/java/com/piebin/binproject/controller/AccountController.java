package com.piebin.binproject.controller;

import com.piebin.binproject.model.dto.AccountRegisterDto;
import com.piebin.binproject.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private static final String API = "/api/account/";

    private final AccountService accountService;

    // Utility
    @PostMapping(API + "register")
    public ResponseEntity<Boolean> register(
            @RequestBody @Valid AccountRegisterDto dto) {
        accountService.register(dto);
        return ResponseEntity.ok(true);
    }
}