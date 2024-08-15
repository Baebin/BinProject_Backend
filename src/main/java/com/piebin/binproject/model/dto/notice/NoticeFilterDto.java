package com.piebin.binproject.model.dto.notice;

import com.piebin.binproject.entity.NoticeSearchFilter;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFilterDto {
    private Integer page = 0;
    private Integer count = 12;

    private NoticeSearchFilter filter = NoticeSearchFilter.ALL;
    private String data;
}
