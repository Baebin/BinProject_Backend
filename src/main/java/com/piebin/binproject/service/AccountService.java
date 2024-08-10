package com.piebin.binproject.service;

import com.piebin.binproject.model.dto.account.AccountLoginDto;
import com.piebin.binproject.model.dto.account.AccountProfileDetailDto;
import com.piebin.binproject.model.dto.account.AccountRegisterDto;
import com.piebin.binproject.model.dto.account.AccountTokenDetailDto;
import com.piebin.binproject.security.SecurityAccount;

public interface AccountService {
    // Utility
    void register(AccountRegisterDto dto);
    AccountTokenDetailDto login(AccountLoginDto dto);

    // Getter
    AccountProfileDetailDto loadProfile(SecurityAccount securityAccount);
}
