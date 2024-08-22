package com.piebin.binproject.model.dto.post_comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.binproject.entity.State;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.PostComment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentDetailDto {
    private Long idx;
    @JsonProperty("parent_idx")
    private Long parentIdx;

    @JsonProperty("author_idx")
    private Long authorIdx;
    @JsonProperty("author_name")
    private String authorName;

    private String comment;
    private State state;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("reg_date")
    private LocalDateTime regDate;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;

    public static PostCommentDetailDto toDto(PostComment postComment) {
        Account author = postComment.getAuthor();
        return PostCommentDetailDto.builder()
                .idx(postComment.getIdx())
                .parentIdx(postComment.getParentComment() != null ? postComment.getParentComment().getIdx() : null)

                .authorIdx(author.getIdx())
                .authorName(author.getName())

                .comment(postComment.getState().equals(State.ENABLED) ? postComment.getComment() : null)
                .state(postComment.getState())

                .regDate(postComment.getRegDate())
                .editedDate(postComment.getEditedDate())
                .build();
    }
}
