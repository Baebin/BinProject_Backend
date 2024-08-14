package com.piebin.binproject.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NoticeErrorCode {
    NOT_FOUND(HttpStatus.BAD_REQUEST, "일치하는 공지사항이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
