package com.piebin.binproject.repository;

import com.piebin.binproject.model.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsById(String id);
    boolean existsByEmail(String email);

    Optional<Account> findById(String id);
}
