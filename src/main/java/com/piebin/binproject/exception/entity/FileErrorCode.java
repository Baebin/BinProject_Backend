package com.piebin.binproject.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FileErrorCode {
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "파일을 찾을 수 없습니다."),
    EXT_INCORRECT(HttpStatus.BAD_REQUEST, "확장자가 올바르지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
