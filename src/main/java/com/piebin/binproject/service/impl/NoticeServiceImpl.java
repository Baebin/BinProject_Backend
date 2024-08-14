package com.piebin.binproject.service.impl;

import com.piebin.binproject.exception.NoticeException;
import com.piebin.binproject.exception.PermissionException;
import com.piebin.binproject.exception.entity.NoticeErrorCode;
import com.piebin.binproject.exception.entity.PermissionErrorCode;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.Notice;
import com.piebin.binproject.model.dto.image.ImageDetailDto;
import com.piebin.binproject.model.dto.image.ImageDto;
import com.piebin.binproject.model.dto.notice.*;
import com.piebin.binproject.repository.NoticeRepository;
import com.piebin.binproject.security.SecurityAccount;
import com.piebin.binproject.service.ImageService;
import com.piebin.binproject.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    private final ImageService imageService;

    // Utility
    @Override
    @Transactional
    public void create(SecurityAccount securityAccount, NoticeCreateDto dto) {
        Account account = securityAccount.getAccount();

        Notice notice = Notice.builder()
                .author(account)
                .title(dto.getTitle())
                .text(dto.getText())
                .build();
        noticeRepository.save(notice);
    }

    // Getter
    @Override
    @Transactional(readOnly = true)
    public NoticeDetailDto load(SecurityAccount securityAccount, NoticeIdxDto dto) {
        Notice notice = noticeRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        return NoticeDetailDto.toDto(notice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeDetailDto> loadAll(SecurityAccount securityAccount, NoticeFilterDto dto) {
        List<NoticeDetailDto> dtos = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getCount());
        for (Notice notice : noticeRepository.findAllByOrderByRegDateDesc(pageRequest))
            dtos.add(NoticeDetailDto.toDtoWithNonText(notice));
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> loadThumbnailImage(SecurityAccount securityAccount, Long idx) throws IOException {
        String path = "notice/" + idx;
        String name = "thumbnail";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        ImageDetailDto imageDetailDto = imageService.download(imageDto);
        return ImageDetailDto.toResponseEntity(imageDetailDto);
    }

    // Setter
    @Override
    @Transactional
    public void edit(SecurityAccount securityAccount, NoticeEditDto dto) {
        Account account = securityAccount.getAccount();
        Notice notice = noticeRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        if (!notice.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.UNAUTHORIZED);
        notice.setTitle(dto.getTitle());
        notice.setText(dto.getText());
    }

    @Override
    @Transactional
    public void editThumbnailImage(SecurityAccount securityAccount, MultipartFile file, NoticeIdxDto dto) throws IOException {
        Account account = securityAccount.getAccount();
        Notice notice = noticeRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        if (!notice.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.UNAUTHORIZED);

        String path = "notice/" + notice.getIdx();
        String name = "thumbnail";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.upload(file, imageDto);
    }

    // Deleter
    @Override
    @Transactional
    public void deleteThumbnailImage(SecurityAccount securityAccount, NoticeIdxDto dto) {
        Account account = securityAccount.getAccount();
        Notice notice = noticeRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        if (!notice.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.UNAUTHORIZED);

        String path = "notice/" + notice.getIdx();
        String name = "thumbnail";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.delete(imageDto);
    }
}
