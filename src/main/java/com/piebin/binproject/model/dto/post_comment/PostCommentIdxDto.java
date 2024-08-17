package com.piebin.binproject.model.dto.post_comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentIdxDto {
    @NotNull(message = "댓글 고유 식별 번호를 입력해주세요.")
    @JsonProperty("idx")
    private Long idx;
}
