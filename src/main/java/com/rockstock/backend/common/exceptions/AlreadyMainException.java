package com.rockstock.backend.common.exceptions;

public class AlreadyMainException extends RuntimeException {
    public AlreadyMainException(String message) {
        super(message);
    }
}
