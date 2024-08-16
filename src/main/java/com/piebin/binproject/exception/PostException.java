package com.piebin.binproject.exception;

import com.piebin.binproject.exception.entity.PostErrorCode;
import lombok.Getter;

@Getter
public class PostException extends RuntimeException {
    private PostErrorCode errorCode;

    public PostException(PostErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
