package com.piebin.binproject.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.binproject.exception.entity.PermissionErrorCode;
import com.piebin.binproject.exception.entity.SystemErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    @JsonProperty("http_status")
    private HttpStatus httpStatus;
    private String message;

    // Authority
    public ErrorDto(PermissionErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    public ErrorDto(SystemErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }
}
