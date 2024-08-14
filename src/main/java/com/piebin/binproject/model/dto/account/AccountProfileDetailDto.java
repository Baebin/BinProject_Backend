package com.piebin.binproject.model.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.binproject.model.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("reg_date")
    private LocalDateTime regDate;

    public static AccountProfileDetailDto toDto(Account account) {
        return AccountProfileDetailDto.builder()
                .idx(account.getIdx())
                .id(account.getId())
                .name(account.getName())
                .phone(account.getPhone())
                .email(account.getEmail())
                .regDate(account.getRegDate())
                .build();
    }
    public static AccountProfileDetailDto toCommonDto(Account account) {
        return AccountProfileDetailDto.builder()
                .idx(account.getIdx())
                .id(account.getId())
                .name(account.getName())
                .regDate(account.getRegDate())
                .build();
    }
}
