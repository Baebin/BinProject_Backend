package com.piebin.binproject.service;

import com.piebin.binproject.model.dto.post.PostIdxDto;
import com.piebin.binproject.model.dto.post_comment.PostCommentCreateDto;
import com.piebin.binproject.model.dto.post_comment.PostCommentDetailDto;
import com.piebin.binproject.model.dto.post_comment.PostCommentIdxDto;
import com.piebin.binproject.security.SecurityAccount;

import java.util.List;

public interface PostCommentService {
    // Utility
    void create(SecurityAccount securityAccount, PostCommentCreateDto dto);

    // Getter
    List<List<PostCommentDetailDto>> loadAll(SecurityAccount securityAccount, PostIdxDto dto);

    // Deleter
    void delete(SecurityAccount securityAccount, PostCommentIdxDto dto);
}
