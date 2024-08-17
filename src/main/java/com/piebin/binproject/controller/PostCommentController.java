package com.piebin.binproject.controller;

import com.piebin.binproject.model.dto.post.*;
import com.piebin.binproject.model.dto.post_comment.PostCommentCreateDto;
import com.piebin.binproject.model.dto.post_comment.PostCommentDetailDto;
import com.piebin.binproject.model.dto.post_comment.PostCommentIdxDto;
import com.piebin.binproject.model.dto.post_comment.PostCommentLikeDto;
import com.piebin.binproject.security.SecurityAccount;
import com.piebin.binproject.service.PostCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostCommentController {
    private static final String API = "/api/post/comment/";

    private final PostCommentService postCommentService;

    // Utility
    @PostMapping(API + "create")
    public ResponseEntity<Boolean> create(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid PostCommentCreateDto dto) {
        postCommentService.create(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    // Getter
    @GetMapping(API + "load/all")
    public ResponseEntity<List<List<PostCommentDetailDto>>> loadAll(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid PostIdxDto dto) {
        return new ResponseEntity<>(
                postCommentService.loadAll(securityAccount, dto), HttpStatus.OK);
    }

    // Setter
    @PutMapping(API + "edit/like")
    public ResponseEntity<Boolean> editLike(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid PostCommentLikeDto dto) {
        postCommentService.editLike(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    // Deleter
    @DeleteMapping(API + "delete")
    public ResponseEntity<Boolean> delete(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid PostCommentIdxDto dto) {
        postCommentService.delete(securityAccount, dto);
        return ResponseEntity.ok(true);
    }
}
