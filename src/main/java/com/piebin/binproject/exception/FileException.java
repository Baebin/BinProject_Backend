package com.piebin.binproject.exception;

import com.piebin.binproject.exception.entity.FileErrorCode;
import lombok.Getter;

@Getter
public class FileException extends RuntimeException {
    private FileErrorCode errorCode;

    public FileException(FileErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
