package com.piebin.binproject.controller;

import com.piebin.binproject.model.dto.notice.*;
import com.piebin.binproject.security.SecurityAccount;
import com.piebin.binproject.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private static final String API = "/api/notice/";

    private final NoticeService noticeService;

    // Utility
    @Secured("ROLE_ADMIN")
    @PostMapping(API + "create")
    public ResponseEntity<Boolean> create(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid NoticeCreateDto dto) {
        noticeService.create(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    // Getter
    @GetMapping(API + "load")
    public ResponseEntity<NoticeDetailDto> load(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid NoticeIdxDto dto) {
        return new ResponseEntity<>(
                noticeService.load(securityAccount, dto), HttpStatus.OK);
    }

    @GetMapping(API + "load/all")
    public ResponseEntity<List<NoticeDetailDto>> loadAll(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid NoticeFilterDto dto) {
        return new ResponseEntity<>(
                noticeService.loadAll(securityAccount, dto), HttpStatus.OK);
    }

    @GetMapping(API + "load/image/thumbnail/{idx}")
    public ResponseEntity<byte[]> loadThumbnailImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @PathVariable("idx") Long idx) throws IOException {
        return noticeService.loadThumbnailImage(securityAccount, idx);
    }

    @GetMapping(API + "load/image/{idx}/{fileIdx}")
    public ResponseEntity<byte[]> loadImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @PathVariable("idx") Long idx,
            @PathVariable("fileIdx") Long fileIdx) throws IOException {
        return noticeService.loadImage(securityAccount, idx, fileIdx);
    }

    // Setter
    @Secured("ROLE_ADMIN")
    @PutMapping(API + "edit")
    public ResponseEntity<Boolean> edit(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid NoticeEditDto dto) {
        noticeService.edit(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(API + "edit/image/thumbnail")
    public ResponseEntity<Boolean> editThumbnailImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "dto") @Valid NoticeIdxDto dto) throws IOException {
        noticeService.editThumbnailImage(securityAccount, file, dto);
        return ResponseEntity.ok(true);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(API + "edit/image/all")
    public ResponseEntity<Boolean> editImages(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestPart(value = "file") List<MultipartFile> files,
            @RequestPart(value = "dto") @Valid NoticeIdxDto dto) throws IOException {
        noticeService.editImages(securityAccount, files, dto);
        return ResponseEntity.ok(true);
    }

    // Deleter
    @Secured("ROLE_ADMIN")
    @DeleteMapping(API + "delete/image/thumbnail")
    public ResponseEntity<Boolean> deleteThumbnailImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid NoticeIdxDto dto) {
        noticeService.deleteThumbnailImage(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(API + "delete/image/all")
    public ResponseEntity<Boolean> deleteImages(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid NoticeIdxDto dto) {
        noticeService.deleteImages(securityAccount, dto);
        return ResponseEntity.ok(true);
    }
}
