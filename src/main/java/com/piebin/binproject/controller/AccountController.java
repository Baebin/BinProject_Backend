package com.piebin.binproject.controller;

import com.piebin.binproject.model.dto.account.*;
import com.piebin.binproject.security.SecurityAccount;
import com.piebin.binproject.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping(API + "login")
    public ResponseEntity<AccountTokenDetailDto> login(
            @RequestBody @Valid AccountLoginDto dto) {
        return new ResponseEntity<>(
                accountService.login(dto), HttpStatus.OK);
    }

    // Getter
    @GetMapping(API + "load/profile")
    public ResponseEntity<AccountProfileDetailDto> loadProfile(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return new ResponseEntity<>(
                accountService.loadProfile(securityAccount), HttpStatus.OK);
    }

    @GetMapping(API + "load/profile/image/{idx}")
    public ResponseEntity<byte[]> loadProfileImage(
            @PathVariable("idx") Long idx) throws IOException {
        return accountService.loadProfileImage(idx);
    }

    // Setter
    @PatchMapping(API + "edit/profile/image")
    public ResponseEntity<Boolean> editProfileImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestPart(value = "file") MultipartFile file) throws IOException {
        accountService.editProfileImage(securityAccount, file);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(API + "edit/name")
    public ResponseEntity<Boolean> editName(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid AccountNameDto dto) {
        accountService.editName(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(API + "edit/password")
    public ResponseEntity<Boolean> editPassword(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid AccountPasswordDto dto) {
        accountService.editPassword(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(API + "edit/phone")
    public ResponseEntity<Boolean> editPhone(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid AccountPhoneDto dto) {
        accountService.editPhone(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(API + "edit/email")
    public ResponseEntity<Boolean> editEmail(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid AccountEmailDto dto) {
        accountService.editEmail(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    // Deleter
    @DeleteMapping(API + "delete/profile/image")
    public ResponseEntity<Boolean> deleteProfileImage(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        accountService.deleteProfileImage(securityAccount);
        return ResponseEntity.ok(true);
    }
}
