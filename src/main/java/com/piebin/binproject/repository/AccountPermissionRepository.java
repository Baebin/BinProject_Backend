package com.piebin.binproject.repository;

import com.piebin.binproject.model.domain.AccountPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPermissionRepository extends JpaRepository<AccountPermission, Long> {
}
