package com.piebin.binproject.model.dto.notice;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCreateDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String text;
}
