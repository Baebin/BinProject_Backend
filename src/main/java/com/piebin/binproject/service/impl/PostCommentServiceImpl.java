package com.piebin.binproject.service.impl;

import com.piebin.binproject.entity.State;
import com.piebin.binproject.exception.PermissionException;
import com.piebin.binproject.exception.PostException;
import com.piebin.binproject.exception.entity.PermissionErrorCode;
import com.piebin.binproject.exception.entity.PostErrorCode;
import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.Post;
import com.piebin.binproject.model.domain.PostComment;
import com.piebin.binproject.model.domain.PostLike;
import com.piebin.binproject.model.dto.post.PostIdxDto;
import com.piebin.binproject.model.dto.post_comment.*;
import com.piebin.binproject.repository.PostCommentRepository;
import com.piebin.binproject.repository.PostLikeRepository;
import com.piebin.binproject.repository.PostRepository;
import com.piebin.binproject.security.SecurityAccount;
import com.piebin.binproject.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;

    // Utility
    @Override
    @Transactional
    public void create(SecurityAccount securityAccount, PostCommentCreateDto dto) {
        Account account = securityAccount.getAccount();

        // Post
        Post post = postRepository.findByIdxAndState(dto.getPostIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));
        // Parent Comment
        PostComment parentComment = null;
        if (dto.getParentCommentIdx() != null) {
            parentComment = postCommentRepository.findByPost_IdxAndIdxAndState(post.getIdx(), dto.getParentCommentIdx(), State.ENABLED)
                    .orElseThrow(() -> new PostException(PostErrorCode.COMMENT_NOT_FOUND));
            if (parentComment.getParentComment() != null)
                parentComment = parentComment.getParentComment();
        }

        PostComment postComment = PostComment.builder()
                .post(post)

                .author(account)
                .comment(dto.getComment())

                .parentComment(parentComment)
                .build();
        postCommentRepository.save(postComment);
    }

    // Getter
    @Override
    @Transactional(readOnly = true)
    public List<List<PostCommentDetailDto>> loadAll(SecurityAccount securityAccount, PostIdxDto dto) {
        // Post
        Post post = postRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));

        Map<Long, List<PostCommentDetailDto>> commentMap = new HashMap<>();
        for (PostComment comment : post.getComments()) {
            long key = (comment.getParentComment() != null ? comment.getParentComment().getIdx() : comment.getIdx());

            PostCommentDetailDto detailDto = PostCommentDetailDto.toDto(comment);
            if (!commentMap.containsKey(key))
                commentMap.put(key, new ArrayList<>(Collections.singletonList(detailDto)));
            else commentMap.get(key).add(detailDto);
        }

        List<List<PostCommentDetailDto>> dtos = new ArrayList<>();
        for (Long key : commentMap.keySet())
            dtos.add(commentMap.get(key));
        return dtos;
    }

    // Setter
    @Override
    @Transactional
    public void edit(SecurityAccount securityAccount, PostCommentEditDto dto) {
        Account account = securityAccount.getAccount();

        // Permission Check
        PostComment postComment = postCommentRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.COMMENT_NOT_FOUND));
        if (!postComment.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        if (!postComment.getComment().equals(dto.getComment()))
            postComment.setEditedDate(LocalDateTime.now());
        postComment.setComment(dto.getComment());
    }

    @Override
    @Transactional
    public void editLike(SecurityAccount securityAccount, PostCommentLikeDto dto) {
        Account account = securityAccount.getAccount();

        // Post
        Post post = postRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.NOT_FOUND));

        if (dto.getLike()) {
            if (post.hasLike(account))
                return;
            PostLike postLike = PostLike.builder()
                    .post(post)
                    .account(account)
                    .build();
            postLikeRepository.save(postLike);
        } else {
            if (!post.hasLike(account))
                return;
            postLikeRepository.deleteAllByPostAndAccount(post, account);
        }
    }

    // Deleter
    @Override
    @Transactional
    public void delete(SecurityAccount securityAccount, PostCommentIdxDto dto) {
        Account account = securityAccount.getAccount();

        // Permission Check
        PostComment postComment = postCommentRepository.findByIdxAndState(dto.getIdx(), State.ENABLED)
                .orElseThrow(() -> new PostException(PostErrorCode.COMMENT_NOT_FOUND));
        if (!postComment.getAuthor().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.FORBIDDEN);

        postComment.setState(State.DISABLED);
    }
}
