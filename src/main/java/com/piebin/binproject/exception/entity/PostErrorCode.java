package com.piebin.binproject.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode {
    NOT_FOUND(HttpStatus.BAD_REQUEST, "일치하는 게시글이 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "일치하는 댓글이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
