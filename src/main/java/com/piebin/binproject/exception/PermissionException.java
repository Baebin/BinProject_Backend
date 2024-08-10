package com.piebin.binproject.exception;

import com.piebin.binproject.exception.entity.PermissionErrorCode;
import lombok.Getter;

@Getter
public class PermissionException extends RuntimeException {
    private PermissionErrorCode errorCode;

    public PermissionException(PermissionErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
