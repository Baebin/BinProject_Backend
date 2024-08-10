package com.piebin.binproject.exception;

import com.piebin.binproject.exception.entity.SystemErrorCode;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {
    private SystemErrorCode errorCode;

    public SystemException(SystemErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
