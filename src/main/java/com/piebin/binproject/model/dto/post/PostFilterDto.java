package com.piebin.binproject.model.dto.post;

import com.piebin.binproject.entity.filter.NoticeSearchFilter;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostFilterDto {
    private Integer page = 0;
    private Integer count = 12;

    private NoticeSearchFilter filter = NoticeSearchFilter.ALL;
    private String data;
}
