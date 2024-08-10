package com.piebin.binproject.service.impl;

import com.piebin.binproject.entity.Permission;
import com.piebin.binproject.exception.AccountException;
import com.piebin.binproject.exception.entity.AccountErrorCode;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.AccountPermission;
import com.piebin.binproject.model.dto.account.*;
import com.piebin.binproject.model.dto.image.ImageDetailDto;
import com.piebin.binproject.model.dto.image.ImageDto;
import com.piebin.binproject.repository.AccountPermissionRepository;
import com.piebin.binproject.repository.AccountRepository;
import com.piebin.binproject.security.SecurityAccount;
import com.piebin.binproject.security.TokenProvider;
import com.piebin.binproject.service.AccountService;
import com.piebin.binproject.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountPermissionRepository accountPermissionRepository;

    private final ImageService imageService;

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    // Utility
    @Override
    @Transactional
    public void register(AccountRegisterDto dto) {
        if (accountRepository.existsById(dto.getId()))
            throw new AccountException(AccountErrorCode.ID_DUPLICATED);
        /*
        if (accountRepository.existsByEmail(dto.getEmail()))
            throw new AccountException(AccountErrorCode.EMAIL_DUPLICATED);
        */

        Account account = Account.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))

                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
        accountRepository.save(account);

        AccountPermission accountPermission = AccountPermission.builder()
                .account(account)
                .permission(Permission.USER)
                .build();
        accountPermissionRepository.save(accountPermission);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountTokenDetailDto login(AccountLoginDto dto) {
        Account account = accountRepository.findById(dto.getId())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        if (!passwordEncoder.matches(dto.getPassword(), account.getPassword()))
            throw new AccountException(AccountErrorCode.PASSWORD_INCORRECT);
        String token = tokenProvider.createAccessToken(dto.getId());
        return AccountTokenDetailDto.builder()
                .token(token)
                .build();
    }

    // Getter
    @Override
    @Transactional(readOnly = true)
    public AccountProfileDetailDto loadProfile(SecurityAccount securityAccount) {
        Account account = securityAccount.getAccount();
        return AccountProfileDetailDto.toDto(account);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> loadProfileImage(Long idx) throws IOException {
        String path = "user/" + idx;
        String name = "profile";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        ImageDetailDto imageDetailDto = imageService.download(imageDto);
        return ImageDetailDto.toResponseEntity(imageDetailDto);
    }

    // Setter
    @Override
    @Transactional
    public void editProfileImage(SecurityAccount securityAccount, MultipartFile file) throws IOException {
        String path = "user/" + securityAccount.getAccount().getIdx();
        String name = "profile";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.upload(file, imageDto);
    }

    @Override
    @Transactional
    public void editName(SecurityAccount securityAccount, AccountNameDto dto) {
        Account account = accountRepository.findById(securityAccount.getAccount().getId())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        account.setName(dto.getName());
    }

    @Override
    @Transactional
    public void editPassword(SecurityAccount securityAccount, AccountPasswordDto dto) {
        Account account = accountRepository.findById(securityAccount.getAccount().getId())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    @Override
    @Transactional
    public void editPhone(SecurityAccount securityAccount, AccountPhoneDto dto) {
        Account account = accountRepository.findById(securityAccount.getAccount().getId())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        account.setPhone(dto.getPhone());
    }

    @Override
    @Transactional
    public void editEmail(SecurityAccount securityAccount, AccountEmailDto dto) {
        Account account = accountRepository.findById(securityAccount.getAccount().getId())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        account.setEmail(dto.getEmail());
    }

    // Deleter
    @Override
    @Transactional
    public void deleteProfileImage(SecurityAccount securityAccount) {
        String path = "user/" + securityAccount.getAccount().getIdx();
        String name = "profile";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.delete(imageDto);
    }
}
