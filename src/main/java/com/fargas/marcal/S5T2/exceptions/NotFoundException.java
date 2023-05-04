package com.fargas.marcal.S5T2.exceptions;

import java.util.NoSuchElementException;

public class NotFoundException extends RuntimeException{

    private String errorCode;

    public NotFoundException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
