package com.piebin.binproject.controller;

import com.piebin.binproject.model.dto.post.*;
import com.piebin.binproject.security.SecurityAccount;
import com.piebin.binproject.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private static final String API = "/api/post/";

    private final PostService postService;

    // Utility
    @PostMapping(API + "create")
    public ResponseEntity<Boolean> create(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid PostCreateDto dto) {
        postService.create(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PostMapping(API + "create/multipart")
    public ResponseEntity<Boolean> createWithImages(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestPart(value = "file") List<MultipartFile> files,
            @RequestPart(value = "dto") @Valid PostCreateDto dto) throws IOException {
        postService.createWithImages(securityAccount, files, dto);
        return ResponseEntity.ok(true);
    }

    // Getter
    @GetMapping(API + "load")
    public ResponseEntity<PostDetailDto> load(
            HttpServletRequest request,
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid PostIdxDto dto) {
        return new ResponseEntity<>(
                postService.load(request, securityAccount, dto), HttpStatus.OK);
    }

    @GetMapping(API + "load/all")
    public ResponseEntity<List<PostDetailDto>> loadAll(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid PostFilterDto dto) {
        return new ResponseEntity<>(
                postService.loadAll(securityAccount, dto), HttpStatus.OK);
    }

    @GetMapping(API + "load/image/thumbnail/{idx}")
    public ResponseEntity<byte[]> loadThumbnailImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @PathVariable("idx") Long idx) throws IOException {
        return postService.loadThumbnailImage(securityAccount, idx);
    }

    @GetMapping(API + "load/image/{idx}/{fileIdx}")
    public ResponseEntity<byte[]> loadImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @PathVariable("idx") Long idx,
            @PathVariable("fileIdx") Long fileIdx) throws IOException {
        return postService.loadImage(securityAccount, idx, fileIdx);
    }

    // Setter
    @PutMapping(API + "edit")
    public ResponseEntity<Boolean> edit(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid PostEditDto dto) {
        postService.edit(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PutMapping(API + "edit/image/thumbnail")
    public ResponseEntity<Boolean> editThumbnailImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "dto") @Valid PostIdxDto dto) throws IOException {
        postService.editThumbnailImage(securityAccount, file, dto);
        return ResponseEntity.ok(true);
    }

    @PutMapping(API + "edit/image/all")
    public ResponseEntity<Boolean> editImages(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestPart(value = "file") List<MultipartFile> files,
            @RequestPart(value = "dto") @Valid PostIdxDto dto) throws IOException {
        postService.editImages(securityAccount, files, dto);
        return ResponseEntity.ok(true);
    }

    // Deleter
    @DeleteMapping(API + "delete")
    public ResponseEntity<Boolean> delete(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid PostIdxDto dto) {
        postService.delete(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(API + "delete/image/thumbnail")
    public ResponseEntity<Boolean> deleteThumbnailImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid PostIdxDto dto) {
        postService.deleteThumbnailImage(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(API + "delete/image/all")
    public ResponseEntity<Boolean> deleteImages(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid PostIdxDto dto) {
        postService.deleteImages(securityAccount, dto);
        return ResponseEntity.ok(true);
    }
}
