package com.fargas.marcal.S5T2.exceptions;

import java.util.NoSuchElementException;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String errorCode, String message) {
        super(message);
    }

}
