package com.piebin.binproject.model.dto.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDetailDto {
    private String ext;
    private byte[] bytes;

    public static ResponseEntity<byte[]> toResponseEntity(ImageDetailDto dto) {
        MediaType mediaType = null;
        if (!ObjectUtils.isEmpty(dto.getExt())) {
            if (dto.getExt().contains("jpeg"))
                mediaType = MediaType.IMAGE_JPEG;
            else if (dto.getExt().contains("png"))
                mediaType = MediaType.IMAGE_PNG;
        }
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(dto.getBytes());
    }
}
