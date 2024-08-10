package com.piebin.binproject.model.dto.image;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    @NotBlank(message = "파일 경로를 입력해주세요.")
    private String path;
    @NotBlank(message = "파일명을 입력해주세요.")
    private String name;
    private String ext;
}
