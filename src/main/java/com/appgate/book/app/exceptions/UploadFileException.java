package com.appgate.book.app.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UploadFileException extends RuntimeException {

    @Getter private final HttpStatus httpStatus;

    public UploadFileException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
