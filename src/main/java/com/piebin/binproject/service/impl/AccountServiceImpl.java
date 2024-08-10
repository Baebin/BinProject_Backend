package com.piebin.binproject.service.impl;

import com.piebin.binproject.entity.Permission;
import com.piebin.binproject.exception.AccountException;
import com.piebin.binproject.exception.entity.AccountErrorCode;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.AccountPermission;
import com.piebin.binproject.model.dto.AccountRegisterDto;
import com.piebin.binproject.repository.AccountPermissionRepository;
import com.piebin.binproject.repository.AccountRepository;
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

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(AccountRegisterDto dto) {
        if (accountRepository.existsById(dto.getId()))
            throw new AccountException(AccountErrorCode.ID_DUPLICATED);
        if (accountRepository.existsByEmail(dto.getEmail()))
            throw new AccountException(AccountErrorCode.EMAIL_DUPLICATED);

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
}
