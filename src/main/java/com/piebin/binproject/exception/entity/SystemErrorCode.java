package com.piebin.binproject.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SystemErrorCode {
    UNKNOWN(HttpStatus.BAD_REQUEST, "알 수 없는 오류가 발생했습니다."),
    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, "데이터가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
