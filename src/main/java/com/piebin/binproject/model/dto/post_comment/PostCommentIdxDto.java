package com.piebin.binproject.model.dto.post_comment;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentIdxDto {
    @NotNull(message = "댓글 고유 식별 번호를 입력해주세요.")
    private Long idx;
}
