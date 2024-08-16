package com.piebin.binproject.exception.handler;

import com.piebin.binproject.exception.dto.ErrorDto;
import com.piebin.binproject.exception.*;
import com.piebin.binproject.exception.entity.PermissionErrorCode;
import com.piebin.binproject.exception.entity.SystemErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<?> handleAccountException(AccountException e) {
        ErrorDto response = ErrorDto.builder()
                .httpStatus(e.getErrorCode().getHttpStatus())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(NoticeException.class)
    public ResponseEntity<?> handleNoticeException(NoticeException e) {
        ErrorDto response = ErrorDto.builder()
                .httpStatus(e.getErrorCode().getHttpStatus())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<?> handlePostException(PostException e) {
        ErrorDto response = ErrorDto.builder()
                .httpStatus(e.getErrorCode().getHttpStatus())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<?> handleFileException(FileException e) {
        ErrorDto response = ErrorDto.builder()
                .httpStatus(e.getErrorCode().getHttpStatus())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // Authority
    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<?> handlePermissionException(PermissionException e) {
        ErrorDto response = new ErrorDto(e.getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e) {
        ErrorDto response = new ErrorDto(SystemErrorCode.UNKNOWN);
        if (!ObjectUtils.isEmpty(e.getMessage()))
            if (e.getMessage().contains("Account"))
                response = new ErrorDto(PermissionErrorCode.UNAUTHORIZED);
        log.error(e.getMessage());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // System
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<?> handleSystemException(SystemException e) {
        ErrorDto response = new ErrorDto(e.getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // Etc
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        ErrorDto response = ErrorDto.builder()
                .message(result.getAllErrors().get(0).getDefaultMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
