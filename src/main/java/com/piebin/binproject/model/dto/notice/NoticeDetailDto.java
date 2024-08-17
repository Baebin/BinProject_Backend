package com.piebin.binproject.model.dto.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailDto {
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

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("reg_date")
    private LocalDateTime regDate;

    public static NoticeDetailDto toDto(Notice notice) {
        Account author = notice.getAuthor();
        return NoticeDetailDto.builder()
                .idx(notice.getIdx())

                .authorIdx(author.getIdx())
                .authorName(author.getName())

                .title(notice.getTitle())
                .text(notice.getText())

                .files(notice.getFiles())
                .viewCount(notice.getViews().size())

                .regDate(notice.getRegDate())
                .build();
    }

    public static NoticeDetailDto toDtoWithNonText(Notice notice) {
        Account author = notice.getAuthor();
        return NoticeDetailDto.builder()
                .idx(notice.getIdx())

                .authorIdx(author.getIdx())
                .authorName(author.getName())

                .title(notice.getTitle())

                .regDate(notice.getRegDate())
                .build();
    }
}
