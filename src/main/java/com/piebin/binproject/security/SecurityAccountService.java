package com.piebin.binproject.security;

import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SecurityAccountService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Account account = accountRepository.findById(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException("아이디가 올바르지 않습니다. " + id)
                );
        return new SecurityAccount(account);
    }
}
