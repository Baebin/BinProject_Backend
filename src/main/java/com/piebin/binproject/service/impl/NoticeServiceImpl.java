package com.piebin.binproject.service.impl;

import com.piebin.binproject.entity.filter.NoticeSearchFilter;
import com.piebin.binproject.entity.State;
import com.piebin.binproject.exception.NoticeException;
import com.piebin.binproject.exception.PermissionException;
import com.piebin.binproject.exception.entity.NoticeErrorCode;
import com.piebin.binproject.exception.entity.PermissionErrorCode;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.Notice;
import com.piebin.binproject.model.domain.NoticeView;
import com.piebin.binproject.model.dto.image.ImageDetailDto;
import com.piebin.binproject.model.dto.image.ImageDto;
import com.piebin.binproject.model.dto.image.ImagePathDto;
import com.piebin.binproject.model.dto.notice.*;
import com.piebin.binproject.repository.NoticeRepository;
import com.piebin.binproject.repository.NoticeViewRepository;
import com.piebin.binproject.security.SecurityAccount;
import com.piebin.binproject.service.ImageService;
import com.piebin.binproject.service.NoticeService;
import com.piebin.binproject.utility.IpFinder;
import com.piebin.binproject.utility.LocalDateTimeManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeViewRepository noticeViewRepository;

    private final ImageService imageService;

    // Utility
    @Override
    @Transactional
    public void create(SecurityAccount securityAccount, NoticeCreateDto dto) {
        Account account = securityAccount.getAccount();

        String uuid = UUID.randomUUID().toString();
        Notice notice = Notice.builder()
                .author(account)
                .uuid(uuid)
                .title(dto.getTitle())
                .text(dto.getText())
                .build();
        noticeRepository.save(notice);
    }

    @Override
    @Transactional
    public void createWithImages(SecurityAccount securityAccount, List<MultipartFile> files, NoticeCreateDto dto) throws IOException {
        Account account = securityAccount.getAccount();

        String uuid = UUID.randomUUID().toString();
        Notice notice = Notice.builder()
                .author(account)
                .uuid(uuid)
                .title(dto.getTitle())
                .text(dto.getText())
                .build();
        noticeRepository.save(notice);

        String path = "notice/" + notice.getUuid();

        int idx = 0;
        for (MultipartFile file : files) {
            ImageDto imageDto = ImageDto.builder().path(path).name((idx++) + "").build();
            imageService.upload(file, imageDto);
        }
        notice.setFiles(files.size());
    }

    // Getter
    @Override
    @Transactional
    public NoticeDetailDto load(HttpServletRequest request, SecurityAccount securityAccount, NoticeIdxDto dto) {
        Notice notice = noticeRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        // View Count
        String ip = IpFinder.getClientOP(request);
        if (!noticeViewRepository.existsByNoticeAndIpAndRegDateAfter(notice, ip, LocalDateTimeManager.getStartOfDay())) {
            NoticeView noticeView = NoticeView.builder()
                    .notice(notice)
                    .ip(ip)
                    .build();
            noticeViewRepository.save(noticeView);
        }
        return NoticeDetailDto.toDto(notice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoticeDetailDto> loadAll(SecurityAccount securityAccount, NoticeFilterDto dto) {
        List<NoticeDetailDto> dtos = new ArrayList<>();

        List<Notice> notices;
        PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getCount());
        if (dto.getFilter().equals(NoticeSearchFilter.TITLE) && !ObjectUtils.isEmpty(dto.getData()))
            notices = noticeRepository.findAllByTitleContainsAndStateOrderByRegDateDesc(pageRequest, dto.getData(), State.ENABLED);
        else if (dto.getFilter().equals(NoticeSearchFilter.TEXT) && !ObjectUtils.isEmpty(dto.getData()))
            notices = noticeRepository.findAllByTextContainsAndStateOrderByRegDateDesc(pageRequest, dto.getData(), State.ENABLED);
        else if (!ObjectUtils.isEmpty(dto.getData()))
            notices = noticeRepository.findAllByTitleContainsOrTextContainsAndStateOrderByRegDateDesc(pageRequest, dto.getData(), dto.getData(), State.ENABLED);
        else notices = noticeRepository.findAllByStateOrderByRegDateDesc(pageRequest, State.ENABLED);

        for (Notice notice : notices)
            dtos.add(NoticeDetailDto.toDtoWithNonText(notice));
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> loadThumbnailImage(SecurityAccount securityAccount, Long idx) throws IOException {
        Notice notice = noticeRepository.findByIdxAndState(idx, State.ENABLED)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        String path = "notice/" + notice.getUuid();
        String name = "0";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        ImageDetailDto imageDetailDto = imageService.download(imageDto);
        return ImageDetailDto.toResponseEntity(imageDetailDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> loadImage(SecurityAccount securityAccount, Long idx, Long fileIdx) throws IOException {
        Notice notice = noticeRepository.findByIdxAndState(idx, State.ENABLED)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        String path = "notice/" + notice.getUuid();
        String name = fileIdx + "";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        ImageDetailDto imageDetailDto = imageService.download(imageDto);
        return ImageDetailDto.toResponseEntity(imageDetailDto);
    }

    // Setter
    @Override
    @Transactional
    public void edit(SecurityAccount securityAccount, NoticeEditDto dto) {
        Account account = securityAccount.getAccount();
        Notice notice = noticeRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        if (!notice.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);
        notice.setTitle(dto.getTitle());
        notice.setText(dto.getText());
    }

    @Override
    @Transactional
    public void editThumbnailImage(SecurityAccount securityAccount, MultipartFile file, NoticeIdxDto dto) throws IOException {
        Account account = securityAccount.getAccount();
        Notice notice = noticeRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        if (!notice.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        String path = "notice/" + notice.getUuid();
        // String name = "thumbnail";
        String name = "0";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.upload(file, imageDto);
    }

    @Override
    @Transactional
    public void editImages(SecurityAccount securityAccount, List<MultipartFile> files, NoticeIdxDto dto) throws IOException {
        Account account = securityAccount.getAccount();
        Notice notice = noticeRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        if (!notice.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        String path = "notice/" + notice.getUuid();

        int idx = 0;
        for (MultipartFile file : files) {
            ImageDto imageDto = ImageDto.builder().path(path).name((idx++) + "").build();
            imageService.upload(file, imageDto);
        }
        notice.setFiles(files.size());
    }

    // Deleter
    @Override
    @Transactional
    public void delete(SecurityAccount securityAccount, NoticeIdxDto dto) {
        Account account = securityAccount.getAccount();
        Notice notice = noticeRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        if (!notice.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);
        
        notice.setState(State.DISABLED);
    }
    
    @Override
    @Transactional
    public void deleteThumbnailImage(SecurityAccount securityAccount, NoticeIdxDto dto) {
        Account account = securityAccount.getAccount();
        Notice notice = noticeRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        if (!notice.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        String path = "notice/" + notice.getUuid();
        // String name = "thumbnail";
        String name = "0";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.delete(imageDto);
    }

    @Override
    @Transactional
    public void deleteImages(SecurityAccount securityAccount, NoticeIdxDto dto) {
        Account account = securityAccount.getAccount();
        Notice notice = noticeRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOT_FOUND));
        if (!notice.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        String path = "notice/" + notice.getUuid();
        ImagePathDto imageDto = ImagePathDto.builder().path(path).build();
        imageService.deleteAllByPathLike(imageDto);

        notice.setFiles(0);
    }
}
