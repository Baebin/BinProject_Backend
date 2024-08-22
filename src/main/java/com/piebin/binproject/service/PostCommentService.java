package com.piebin.binproject.service;

import com.piebin.binproject.model.dto.post.PostIdxDto;
import com.piebin.binproject.model.dto.post_comment.*;
import com.piebin.binproject.security.SecurityAccount;

import java.util.List;

public interface PostCommentService {
    // Utility
    void create(SecurityAccount securityAccount, PostCommentCreateDto dto);

    // Getter
    List<List<PostCommentDetailDto>> loadAll(SecurityAccount securityAccount, PostIdxDto dto);

    // Setter
    void edit(SecurityAccount securityAccount, PostCommentEditDto dto);
    void editLike(SecurityAccount securityAccount, PostCommentLikeDto dto);

    // Deleter
    void delete(SecurityAccount securityAccount, PostCommentIdxDto dto);
}
