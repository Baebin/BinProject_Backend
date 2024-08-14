package com.piebin.binproject.service;

import com.piebin.binproject.model.dto.account.*;
import com.piebin.binproject.security.SecurityAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AccountService {
    // Utility
    void register(AccountRegisterDto dto);
    AccountLoginDetailDto login(AccountLoginDto dto);

    // Getter
    AccountProfileDetailDto loadProfile(SecurityAccount securityAccount);
    ResponseEntity<byte[]> loadProfileImage(Long idx) throws IOException;

    // Setter
    void editProfileImage(SecurityAccount securityAccount, MultipartFile file) throws IOException;
    void editName(SecurityAccount securityAccount, AccountNameDto dto);
    void editPassword(SecurityAccount securityAccount, AccountPasswordDto dto);
    void editPhone(SecurityAccount securityAccount, AccountPhoneDto dto);
    void editEmail(SecurityAccount securityAccount, AccountEmailDto dto);

    // Deleter
    void deleteProfileImage(SecurityAccount securityAccount);
}
