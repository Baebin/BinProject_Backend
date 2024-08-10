package com.piebin.binproject.model.dto.account;

import com.piebin.binproject.model.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountProfileDetailDto {
    private Long idx;
    private String id;
    private String name;
    private String phone;
    private String email;

    public static AccountProfileDetailDto toDto(Account account) {
        return AccountProfileDetailDto.builder()
                .idx(account.getIdx())
                .id(account.getId())
                .name(account.getName())
                .phone(account.getPhone())
                .email(account.getEmail())
                .build();
    }
}
