package com.piebin.binproject.service;

import com.piebin.binproject.model.dto.notice.*;
import com.piebin.binproject.security.SecurityAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NoticeService {
    // Utility
    void create(SecurityAccount securityAccount, NoticeCreateDto dto);

    // Getter
    NoticeDetailDto load(SecurityAccount securityAccount, NoticeIdxDto dto);
    List<NoticeDetailDto> loadAll(SecurityAccount securityAccount, NoticeFilterDto dto);
    ResponseEntity<byte[]> loadThumbnailImage(SecurityAccount securityAccount, Long idx) throws IOException;

    // Setter
    void edit(SecurityAccount securityAccount, NoticeEditDto dto);
    void editThumbnailImage(SecurityAccount securityAccount, MultipartFile file, NoticeIdxDto dto) throws IOException;

    // Deleter
    void deleteThumbnailImage(SecurityAccount securityAccount, NoticeIdxDto dto);
}
