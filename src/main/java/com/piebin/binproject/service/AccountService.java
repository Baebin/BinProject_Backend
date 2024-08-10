package com.piebin.binproject.service;

import com.piebin.binproject.model.dto.AccountLoginDto;
import com.piebin.binproject.model.dto.AccountRegisterDto;
import com.piebin.binproject.model.dto.AccountTokenDetailDto;

public interface AccountService {
    // Utility
    void register(AccountRegisterDto dto);
    AccountTokenDetailDto login(AccountLoginDto dto);
}
