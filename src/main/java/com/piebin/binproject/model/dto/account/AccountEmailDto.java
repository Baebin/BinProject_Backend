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
public class AccountEmailDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
}
