package com.piebin.binproject.model.dto.post_comment;

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
public class PostCommentEditDto {
    @NotNull(message = "댓글 고유 식별 번호를 입력해주세요.")
    private Long idx;
    @NotBlank(message = "댓글을 입력해주세요.")
    private String comment;
}
