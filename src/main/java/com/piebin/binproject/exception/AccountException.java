package com.piebin.binproject.exception;

import com.piebin.binproject.exception.entity.AccountErrorCode;
import lombok.Getter;

@Getter
public class AccountException extends RuntimeException {
    private AccountErrorCode errorCode;

    public AccountException(AccountErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
