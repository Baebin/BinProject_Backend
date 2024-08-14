package com.piebin.binproject.model.dto.notice;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeIdxDto {
    @NotNull(message = "공지사항 고유 식별 번호를 입력해주세요.")
    private Long idx;
}
