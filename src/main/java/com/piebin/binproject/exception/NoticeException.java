package com.piebin.binproject.exception;

import com.piebin.binproject.exception.entity.NoticeErrorCode;
import lombok.Getter;

@Getter
public class NoticeException extends RuntimeException {
    private NoticeErrorCode errorCode;

    public NoticeException(NoticeErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
