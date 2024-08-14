package com.piebin.binproject.model.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountIdxDto {
    @NotNull(message = "계정 고유 식별 번호를 입력해주세요.")
    private Long idx;
}
