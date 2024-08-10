package com.piebin.binproject.service.impl;

import com.piebin.binproject.entity.Permission;
import com.piebin.binproject.exception.AccountException;
import com.piebin.binproject.exception.entity.AccountErrorCode;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.AccountPermission;
import com.piebin.binproject.model.dto.AccountLoginDto;
import com.piebin.binproject.model.dto.AccountProfileDetailDto;
import com.piebin.binproject.model.dto.AccountRegisterDto;
import com.piebin.binproject.model.dto.AccountTokenDetailDto;
import com.piebin.binproject.repository.AccountPermissionRepository;
import com.piebin.binproject.repository.AccountRepository;
import com.piebin.binproject.security.SecurityAccount;
import com.piebin.binproject.security.TokenProvider;
import com.piebin.binproject.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountPermissionRepository accountPermissionRepository;

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
}
