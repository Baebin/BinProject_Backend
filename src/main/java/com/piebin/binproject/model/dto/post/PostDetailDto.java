package com.piebin.binproject.model.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDto {
    private Long idx;

    @JsonProperty("author_idx")
    private Long authorIdx;
    @JsonProperty("author_name")
    private String authorName;

    private String title;
    private String text;

    private Integer files;
    @JsonProperty("view_count")
    private Integer viewCount;
    @JsonProperty("comment_count")
    private Integer commentCount;

    @JsonProperty("like_state")
    private Boolean likeState;
    @JsonProperty("like_count")
    private Integer likeCount;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("reg_date")
    private LocalDateTime regDate;

    public static PostDetailDto toDto(Account account, Post post) {
        Account author = post.getAuthor();
        return PostDetailDto.builder()
                .idx(post.getIdx())

                .authorIdx(author.getIdx())
                .authorName(author.getName())

                .title(post.getTitle())
                .text(post.getText())

                .files(post.getFiles())
                .viewCount(post.getViews().size())
                .commentCount(post.getComments().size())

                .likeState(account != null ? post.hasLike(account) : false)
                .likeCount(post.getLikes().size())

                .regDate(post.getRegDate())
                .build();
    }

    public static PostDetailDto toDtoWithNonText(Post post) {
        Account author = post.getAuthor();
        return PostDetailDto.builder()
                .idx(post.getIdx())

                .authorIdx(author.getIdx())
                .authorName(author.getName())

                .title(post.getTitle())

                .regDate(post.getRegDate())
                .build();
    }
}
