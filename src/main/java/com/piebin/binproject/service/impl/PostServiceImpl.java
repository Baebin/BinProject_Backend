package com.piebin.binproject.service.impl;

import com.piebin.binproject.entity.filter.PostSearchFilter;
import com.piebin.binproject.entity.State;
import com.piebin.binproject.exception.PostException;
import com.piebin.binproject.exception.PermissionException;
import com.piebin.binproject.exception.entity.PostErrorCode;
import com.piebin.binproject.exception.entity.PermissionErrorCode;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.Post;
import com.piebin.binproject.model.domain.PostView;
import com.piebin.binproject.model.dto.image.ImageDetailDto;
import com.piebin.binproject.model.dto.image.ImageDto;
import com.piebin.binproject.model.dto.image.ImagePathDto;
import com.piebin.binproject.model.dto.post.*;
import com.piebin.binproject.repository.PostRepository;
import com.piebin.binproject.repository.PostViewRepository;
import com.piebin.binproject.security.SecurityAccount;
import com.piebin.binproject.service.ImageService;
import com.piebin.binproject.service.PostService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostViewRepository postViewRepository;

    private final ImageService imageService;

    // Utility
    @Override
    @Transactional
    public void create(SecurityAccount securityAccount, PostCreateDto dto) {
        Account account = securityAccount.getAccount();

        String uuid = UUID.randomUUID().toString();
        Post post = Post.builder()
                .author(account)
                .uuid(uuid)
                .title(dto.getTitle())
                .text(dto.getText())
                .build();
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void createWithImages(SecurityAccount securityAccount, List<MultipartFile> files, PostCreateDto dto) throws IOException {
        Account account = securityAccount.getAccount();

        String uuid = UUID.randomUUID().toString();
        Post post = Post.builder()
                .author(account)
                .uuid(uuid)
                .title(dto.getTitle())
                .text(dto.getText())
                .build();
        postRepository.save(post);

        String path = "post/" + post.getUuid();

        int idx = 0;
        for (MultipartFile file : files) {
            ImageDto imageDto = ImageDto.builder().path(path).name((idx++) + "").build();
            imageService.upload(file, imageDto);
        }
        post.setFiles(files.size());
    }

    // Getter
    @Override
    @Transactional
    public PostDetailDto load(HttpServletRequest request, SecurityAccount securityAccount, PostIdxDto dto) {
        Post post = postRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));
        // View
        String ip = IpFinder.getClientOP(request);
        if (!postViewRepository.existsByPostAndIpAndRegDateAfter(post, ip, LocalDateTimeManager.getStartOfDay())) {
            PostView postView = PostView.builder()
                    .post(post)
                    .ip(ip)
                    .build();
            postViewRepository.save(postView);
        }
        return PostDetailDto.toDto(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDetailDto> loadAll(SecurityAccount securityAccount, PostFilterDto dto) {
        List<PostDetailDto> dtos = new ArrayList<>();

        List<Post> posts;
        PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getCount());
        if (dto.getFilter().equals(PostSearchFilter.TITLE) && !ObjectUtils.isEmpty(dto.getData()))
            posts = postRepository.findAllByTitleContainsAndStateOrderByRegDateDesc(pageRequest, dto.getData(), State.ENABLED);
        else if (dto.getFilter().equals(PostSearchFilter.TEXT) && !ObjectUtils.isEmpty(dto.getData()))
            posts = postRepository.findAllByTextContainsAndStateOrderByRegDateDesc(pageRequest, dto.getData(), State.ENABLED);
        else if (!ObjectUtils.isEmpty(dto.getData()))
            posts = postRepository.findAllByTitleContainsOrTextContainsAndStateOrderByRegDateDesc(pageRequest, dto.getData(), dto.getData(), State.ENABLED);
        else posts = postRepository.findAllByStateOrderByRegDateDesc(pageRequest, State.ENABLED);

        for (Post post : posts)
            dtos.add(PostDetailDto.toDtoWithNonText(post));
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> loadThumbnailImage(SecurityAccount securityAccount, Long idx) throws IOException {
        Post post = postRepository.findByIdxAndState(idx, State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));
        String path = "post/" + post.getUuid();
        String name = "0";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        ImageDetailDto imageDetailDto = imageService.download(imageDto);
        return ImageDetailDto.toResponseEntity(imageDetailDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> loadImage(SecurityAccount securityAccount, Long idx, Long fileIdx) throws IOException {
        Post post = postRepository.findByIdxAndState(idx, State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));
        String path = "post/" + post.getUuid();
        String name = fileIdx + "";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        ImageDetailDto imageDetailDto = imageService.download(imageDto);
        return ImageDetailDto.toResponseEntity(imageDetailDto);
    }

    // Setter
    @Override
    @Transactional
    public void edit(SecurityAccount securityAccount, PostEditDto dto) {
        Account account = securityAccount.getAccount();
        Post post = postRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));
        if (!post.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);
        post.setTitle(dto.getTitle());
        post.setText(dto.getText());
    }

    @Override
    @Transactional
    public void editThumbnailImage(SecurityAccount securityAccount, MultipartFile file, PostIdxDto dto) throws IOException {
        Account account = securityAccount.getAccount();
        Post post = postRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));
        if (!post.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        String path = "post/" + post.getUuid();
        // String name = "thumbnail";
        String name = "0";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.upload(file, imageDto);
    }

    @Override
    @Transactional
    public void editImages(SecurityAccount securityAccount, List<MultipartFile> files, PostIdxDto dto) throws IOException {
        Account account = securityAccount.getAccount();
        Post post = postRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));
        if (!post.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        String path = "post/" + post.getUuid();

        int idx = 0;
        for (MultipartFile file : files) {
            ImageDto imageDto = ImageDto.builder().path(path).name((idx++) + "").build();
            imageService.upload(file, imageDto);
        }
        post.setFiles(files.size());
    }

    // Deleter
    @Override
    @Transactional
    public void delete(SecurityAccount securityAccount, PostIdxDto dto) {
        Account account = securityAccount.getAccount();
        Post post = postRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));
        if (!post.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        post.setState(State.DISABLED);
    }

    @Override
    @Transactional
    public void deleteThumbnailImage(SecurityAccount securityAccount, PostIdxDto dto) {
        Account account = securityAccount.getAccount();
        Post post = postRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));
        if (!post.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        String path = "post/" + post.getUuid();
        // String name = "thumbnail";
        String name = "0";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.delete(imageDto);
    }

    @Override
    @Transactional
    public void deleteImages(SecurityAccount securityAccount, PostIdxDto dto) {
        Account account = securityAccount.getAccount();
        Post post = postRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));
        if (!post.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        String path = "post/" + post.getUuid();
        ImagePathDto imageDto = ImagePathDto.builder().path(path).build();
        imageService.deleteAllByPathLike(imageDto);

        post.setFiles(0);
    }
}
