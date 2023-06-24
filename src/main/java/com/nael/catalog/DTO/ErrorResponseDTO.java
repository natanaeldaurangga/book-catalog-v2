package com.nael.catalog.DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.nael.catalog.enums.ErrorCode;

import lombok.Data;

@Data
public class ErrorResponseDTO implements Serializable {

    private Date timestamps;

    private String message;

    private ErrorCode errorCode;

    private List<String> details;

    private HttpStatus status;

    public ErrorResponseDTO(String message, ErrorCode errorCode, List<String> details,
            HttpStatus status) {
        this.timestamps = new Date();
        this.message = message;
        this.errorCode = errorCode;
        this.details = details;
        this.status = status;
    }

    public static ErrorResponseDTO of(final String message, List<String> details, final ErrorCode errorCode,
            HttpStatus status) {
        return new ErrorResponseDTO(message, errorCode, details, status);
    }

}
