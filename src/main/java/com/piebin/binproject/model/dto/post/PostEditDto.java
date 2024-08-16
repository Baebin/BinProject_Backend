package com.piebin.binproject.model.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEditDto {
    @NotNull(message = "게시글 고유 식별 번호를 입력해주세요.")
    private Long idx;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String text;
}
