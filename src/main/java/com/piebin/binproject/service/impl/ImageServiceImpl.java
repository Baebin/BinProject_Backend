package com.piebin.binproject.service.impl;

import com.piebin.binproject.exception.FileException;
import com.piebin.binproject.exception.entity.FileErrorCode;
import com.piebin.binproject.model.domain.Image;
import com.piebin.binproject.model.dto.image.ImageDetailDto;
import com.piebin.binproject.model.dto.image.ImageDto;
import com.piebin.binproject.model.dto.image.ImagePathDto;
import com.piebin.binproject.repository.ImageRepository;
import com.piebin.binproject.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    public static final String DIRECTORY = "images";

    private final ImageRepository imageRepository;

    private String getPath() {
        return new File("").getAbsolutePath() + "/";
    }

    private String getFilePath(String path, String name, String ext) {
        return (getPath()
                + DIRECTORY + "/"
                + path + "/"
                + name + "." + ext)
                .replace("\\", "/");
    }

    private String getType(MultipartFile file) {
        return getType(file.getContentType());
    }

    private String getType(String type) {
        if (type.contains("image/gif"))
            return "gif";
        if (type.contains("image/png"))
            return "png";
        if (type.contains("image/jpeg") || type.contains("application/octet-stream"))
            return "jpeg";
        return null;
    }

    // Getter
    @Override
    public File getFile(String path, String name, String ext) {
        return new File(getFilePath(path, name, ext));
    }

    @Override
    @Transactional(readOnly = true)
    public ImageDetailDto download(ImageDto dto) throws IOException {
        Image image = imageRepository.findByPathAndName(dto.getPath(), dto.getName())
                .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));
        File file = getFile(image.getPath(), image.getName(), image.getExt());
        if (!file.exists())
            throw new FileException(FileErrorCode.FILE_NOT_FOUND);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return ImageDetailDto.builder()
                .bytes(bytes)
                .ext(image.getExt())
                .build();
    }

    // Setter
    @Override
    @Transactional
    public void upload(MultipartFile file, ImageDto dto) throws IOException {
        if (file.isEmpty())
            throw new FileException(FileErrorCode.FILE_NOT_FOUND);
        String ext = getType(file);
        if (ObjectUtils.isEmpty(ext))
            throw new FileException(FileErrorCode.EXT_INCORRECT);
        File img = getFile(dto.getPath(), dto.getName(), ext);
        if (!img.exists())
            img.mkdirs();
        if (img.exists())
            img.delete();

        // Upload
        file.transferTo(img);

        // Database Update
        Optional<Image> optional = imageRepository.findByPathAndName(dto.getPath(), dto.getName());
        if (optional.isPresent()) {
            Image image = optional.get();
            image.setExt(ext);
            image.setEditDate(LocalDateTime.now());
        }
        else imageRepository.save(
                Image.builder()
                        .path(dto.getPath())
                        .name(dto.getName())
                        .ext(ext)
                        .build());
    }

    // Deleter
    @Override
    @Transactional
    public void delete(ImageDto dto) {
        Image image = imageRepository.findByPathAndName(dto.getPath(), dto.getName())
                .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));
        File file = getFile(image.getPath(), image.getName(), image.getExt());
        if (!file.exists())
            throw new FileException(FileErrorCode.FILE_NOT_FOUND);
        if (file.delete())
            imageRepository.delete(image);
    }

    @Override
    @Transactional
    public void deleteAllByPathLike(ImagePathDto dto) {
        for (Image image : imageRepository.findAllByPathLike(dto.getPath())) {
            File file = getFile(image.getPath(), image.getName(), image.getExt());
            imageRepository.delete(image);
            file.delete();
        }
    }
}
