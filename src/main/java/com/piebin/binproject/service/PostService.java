package com.piebin.binproject.service;

import com.piebin.binproject.model.dto.post.*;
import com.piebin.binproject.security.SecurityAccount;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    // Utility
    void create(SecurityAccount securityAccount, PostCreateDto dto);
    void createWithImages(SecurityAccount securityAccount, List<MultipartFile> files, PostCreateDto dto) throws IOException;
    
    // Getter
    PostDetailDto load(HttpServletRequest request, SecurityAccount securityAccount, PostIdxDto dto);
    List<PostDetailDto> loadAll(SecurityAccount securityAccount, PostFilterDto dto);
    ResponseEntity<byte[]> loadThumbnailImage(SecurityAccount securityAccount, Long idx) throws IOException;
    ResponseEntity<byte[]> loadImage(SecurityAccount securityAccount, Long idx, Long fileIdx) throws IOException;

    // Setter
    void edit(SecurityAccount securityAccount, PostEditDto dto);
    void editThumbnailImage(SecurityAccount securityAccount, MultipartFile file, PostIdxDto dto) throws IOException;
    void editImages(SecurityAccount securityAccount, List<MultipartFile> files, PostIdxDto dto) throws IOException;

    // Deleter
    void delete(SecurityAccount securityAccount, PostIdxDto dto);
    void deleteThumbnailImage(SecurityAccount securityAccount, PostIdxDto dto);
    void deleteImages(SecurityAccount securityAccount, PostIdxDto dto);
}
