package com.piebin.binproject.service;

import com.piebin.binproject.model.dto.account.AccountLoginDto;
import com.piebin.binproject.model.dto.account.AccountProfileDetailDto;
import com.piebin.binproject.model.dto.account.AccountRegisterDto;
import com.piebin.binproject.model.dto.account.AccountTokenDetailDto;
import com.piebin.binproject.security.SecurityAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AccountService {
    // Utility
    void register(AccountRegisterDto dto);
    AccountTokenDetailDto login(AccountLoginDto dto);

    // Getter
    AccountProfileDetailDto loadProfile(SecurityAccount securityAccount);
    ResponseEntity<byte[]> loadProfileImage(Long idx) throws IOException;

    // Setter
    void editProfileImage(SecurityAccount securityAccount, MultipartFile file) throws IOException;
    void deleteProfileImage(SecurityAccount securityAccount);
}
