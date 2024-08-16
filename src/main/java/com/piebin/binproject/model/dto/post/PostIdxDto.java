package com.piebin.binproject.model.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostIdxDto {
    @NotNull(message = "게시글 고유 식별 번호를 입력해주세요.")
    private Long idx;
}
