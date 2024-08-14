package com.piebin.binproject.model.dto.account;

import com.piebin.binproject.entity.Permission;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.AccountPermission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginDetailDto {
    private Long idx;
    private String token;
    private Permission permission;

    public static AccountLoginDetailDto toDto(Account account, String token) {
        Permission p = Permission.USER;
        for (AccountPermission accountPermission : account.getPermissions()) {
            if (!accountPermission.getPermission().equals(Permission.ADMIN))
                continue;
            p = Permission.ADMIN;
            break;
        }
        return AccountLoginDetailDto.builder()
                .idx(account.getIdx())
                .token(token)
                .permission(p)
                .build();
    }
}
