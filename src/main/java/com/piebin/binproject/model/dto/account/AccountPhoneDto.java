package com.piebin.binproject.model.dto.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPhoneDto {
    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phone;
}
