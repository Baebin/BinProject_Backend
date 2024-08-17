package com.piebin.binproject.model.dto.post_comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentCreateDto {
    @NotNull(message = "댓글 고유 식별 번호를 입력해주세요.")
    @JsonProperty("post_idx")
    private Long postIdx;
    @JsonProperty("parent_comment_idx")
    private Long parentCommentIdx;
    @NotBlank(message = "댓글을 입력해주세요.")
    private String comment;
}
