package com.piebin.binproject.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccountErrorCode {
    NOT_FOUND(HttpStatus.BAD_REQUEST, "일치하는 정보가 없습니다."),
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다."),

    ID_DUPLICATED(HttpStatus.CONFLICT, "사용할 수 없는 아이디입니다."),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "사용할 수 없는 이메일입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
