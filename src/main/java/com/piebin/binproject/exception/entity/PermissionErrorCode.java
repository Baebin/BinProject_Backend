package com.piebin.binproject.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PermissionErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "로그인이 필요합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
