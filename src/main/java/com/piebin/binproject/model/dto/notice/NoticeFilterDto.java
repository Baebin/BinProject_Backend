package com.piebin.binproject.model.dto.notice;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFilterDto {
    private Integer page = 0;
    private Integer count = 12;
}
