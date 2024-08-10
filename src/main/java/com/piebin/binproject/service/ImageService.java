package com.piebin.binproject.service;

import com.piebin.binproject.model.dto.image.ImageDetailDto;
import com.piebin.binproject.model.dto.image.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ImageService {
    // Getter
    File getFile(String path, String name, String ext);
    ImageDetailDto download(ImageDto dto) throws IOException;

    // Setter
    void upload(MultipartFile file, ImageDto dto) throws IOException;

    // Deleter
    void delete(ImageDto dto);
}
